package es.microforum.webfrontend.web.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.access.prepost.PreAuthorize;

import es.microforum.webfrontend.web.form.EmpresaGrid;
import es.microforum.webfrontend.web.form.Message;
import es.microforum.webfrontend.web.util.UrlUtil;

import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpresaService;

@RequestMapping("/empresas")
@Controller
public class EmpresaController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MessageSource messageSource;

	@Autowired
	private EmpresaService empresaService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		logger.info("Listing empresas");
		return "empresas/list";
	}
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yy-mm-dd"), true);
        binder.registerCustomEditor(Date.class, editor);
    }
	
	//Mostrar empresa
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") String id, Model uiModel) {
		Empresa empresa = empresaService.findByNif(id);
		uiModel.addAttribute("empresa", empresa);
		DateTime fecha = new DateTime(empresa.getFechaInicioActividades());
		uiModel.addAttribute("fecha", fecha);
		return "empresas/show";
	}

	//Nuevo empresa
	//@PreAuthorize("hasRole('PRES 0')")
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Empresa empresa, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Creating empresa");
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute(
					"message",
					new Message("error", messageSource.getMessage(
							"empresa_save_fail", new Object[] {}, locale)));
			uiModel.addAttribute("empresa", empresa);
			return "empresa/create";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute(
				"message",
				new Message("success", messageSource.getMessage(
						"empresa_save_success", new Object[] {}, locale)));

		logger.info("Empresa id: " + empresa.getNif());
		
		empresaService.save(empresa);
		return "redirect:/empresas/"
				+ UrlUtil.encodeUrlPathSegment(empresa.getNif(),
						httpServletRequest);
	}
	
	//Mostrar menu de edicion de empresa
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model uiModel) {
		Empresa empresa = empresaService.findByNif(id);
		uiModel.addAttribute("empresa", empresa);
		DateTime fecha = new DateTime(empresa.getFechaInicioActividades());
		uiModel.addAttribute("fecha", fecha);
		return "empresas/update";
	}
	
	//Modificar empresa
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	public String update(@Valid Empresa empresa, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Updating empresa");
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute(
					"message",
					new Message("error", messageSource.getMessage(
							"empresa_save_fail", new Object[] {}, locale)));
			uiModel.addAttribute("empresa", empresa);
			return "empresas/update";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute(
				"message",
				new Message("success", messageSource.getMessage(
						"empresa_save_success", new Object[] {}, locale)));
		
		empresaService.save(empresa);
		return "redirect:"
				+ UrlUtil.encodeUrlPathSegment(empresa.getNif(),
						httpServletRequest);
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		Empresa empresa = new Empresa();
		uiModel.addAttribute("empresa", empresa);
		return "empresas/create";
	}

	//Eliminar empresa
	@RequestMapping(value = "/{nif}", params = "delete", method = RequestMethod.GET)
	public String delete(@PathVariable("nif") String nif, Model uiModel) {
		logger.info("Deleting empleado");
		uiModel.asMap().clear();
		Empresa empresa = empresaService.findByNif(nif);
		empresaService.delete(empresa);
		return "empresas/delete";
	}
	
	/**
	 * Support data for front-end grid
	 * 
	 * @param name
	 * @return
	 */
	//Listar empresa
	@RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public EmpresaGrid listGrid(@RequestParam("nombre") String nombre,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {
		logger.info("Listing empresas for grid with page: {}, rows: {}", page,
				rows);
		logger.info("Listing empresas for grid with sort: {}, order: {}",
				sortBy, order);

		PageRequest pageRequest = null;

		pageRequest = new PageRequest(page - 1, rows);

		EmpresaGrid empresaGrid = new EmpresaGrid();
		Page<Empresa> empresas;
			
		if (nombre == null || nombre.equals("undefined")||nombre.equals("")) {
			empresas = empresaService.findAll(pageRequest);
		} else {
			empresas = empresaService.findByNombre(pageRequest,nombre);
		}
		empresaGrid.setCurrentPage(empresas.getNumber() + 1);
		empresaGrid.setTotalPages(empresas.getTotalPages());
		empresaGrid.setTotalRecords(empresas.getTotalElements());
		empresaGrid.setEmpresaData(empresas.getContent());

		return empresaGrid;
	}


}
