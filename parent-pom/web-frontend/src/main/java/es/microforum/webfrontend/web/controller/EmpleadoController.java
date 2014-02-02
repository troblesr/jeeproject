package es.microforum.webfrontend.web.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.access.prepost.PreAuthorize;

import es.microforum.webfrontend.web.form.EmpleadoGrid;
import es.microforum.webfrontend.web.form.Message;
import es.microforum.webfrontend.web.util.UrlUtil;

import es.microforum.model.Empleado;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.serviceapi.EmpresaService;

@RequestMapping("/empleados")
@Controller
public class EmpleadoController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MessageSource messageSource;

	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private EmpresaService empresaService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model uiModel) {
		logger.info("Listing empleados");
		return "empleados/list";
	}
	
	//Mostrar empleado
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") String id, Model uiModel) {
		Empleado empleado = empleadoService.findById(id);
		uiModel.addAttribute("empleado", empleado);
		return "empleados/show";
	}

	//Nuevo empleado
	//@PreAuthorize("hasRole('PRES 0')")
	@RequestMapping(method = RequestMethod.POST)
	public String create(@RequestParam("empresa") String nif,@Valid Empleado empleado, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Creating empleado");
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute(
					"message",
					new Message("error", messageSource.getMessage(
							"empleado_save_fail", new Object[] {}, locale)));
			uiModel.addAttribute("empleado", empleado);
			return "empleados/create";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute(
				"message",
				new Message("success", messageSource.getMessage(
						"empleado_save_success", new Object[] {}, locale)));

		logger.info("Empleado id: " + empleado.getDni());
		
		empleado.setEmpresa(empresaService.findByNif(nif));
		empleadoService.save(empleado);
		return "redirect:/empleados/"
				+ UrlUtil.encodeUrlPathSegment(empleado.getDni(),
						httpServletRequest);
	}
	
	//Mostrar menu de edicion de empleado
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") String id, Model uiModel) {
		uiModel.addAttribute("empresas",empresaService.findAll());
		Empleado empleado = empleadoService.findByIdWithEmpresa(id);
		//empleado.getEmpresa().setEmpleados(null);
		uiModel.addAttribute("empleado", empleado );
		return "empleados/update";
	}
	
	//Modificar empleado
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.POST)
	public String update(@RequestParam("empresa") String nif,@Valid Empleado empleado, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes, Locale locale) {
		logger.info("Updating empleado");
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute(
					"message",
					new Message("error", messageSource.getMessage(
							"empleado_save_fail", new Object[] {}, locale)));
			uiModel.addAttribute("empleado", empleado);
			return "empleados/update";
		}
		uiModel.asMap().clear();
		redirectAttributes.addFlashAttribute(
				"message",
				new Message("success", messageSource.getMessage(
						"contact_save_success", new Object[] {}, locale)));
		
		empleado.setEmpresa(empresaService.findByNif(nif));
		empleadoService.save(empleado);
		return "redirect:"
				+ UrlUtil.encodeUrlPathSegment(empleado.getDni(),
						httpServletRequest);
	}
	
	//Carga pantalla crear empleado
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		Empleado empleado = new Empleado();
		uiModel.addAttribute("empresas",empresaService.findAll());
		uiModel.addAttribute("empleado", empleado);
		return "empleados/create";
	}
	
	//Eliminar empleado
	@RequestMapping(value = "/{dni}", params = "delete", method = RequestMethod.GET)
	public String delete(@PathVariable("dni") String dni, Model uiModel) {
		logger.info("Deleting empleado");
		uiModel.asMap().clear();
		Empleado empleado = empleadoService.findById(dni);
		empleadoService.delete(empleado);
		return "empleados/delete";
	}
	
	/**
	 * Support data for front-end grid
	 * 
	 * @param name
	 * @return
	 */
	//Listar empleado
	@RequestMapping(value = "/listgrid", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public EmpleadoGrid listGrid(@RequestParam("nombre") String nombre,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sortBy,
			@RequestParam(value = "sord", required = false) String order) {
		logger.info("Listing empleados for grid with page: {}, rows: {}", page,
				rows);
		logger.info("Listing empleados for grid with sort: {}, order: {}",
				sortBy, order);

		PageRequest pageRequest = null;

		pageRequest = new PageRequest(page - 1, rows);

		EmpleadoGrid empleadoGrid = new EmpleadoGrid();
		Page<Empleado> empleados;
			
		if (nombre == null || nombre.equals("undefined")||nombre.equals("")) {
			empleados = empleadoService.findAll(pageRequest);
		} else {
				empleados = empleadoService.findByNombre(pageRequest,nombre);
		}
		empleadoGrid.setCurrentPage(empleados.getNumber() + 1);
		empleadoGrid.setTotalPages(empleados.getTotalPages());
		empleadoGrid.setTotalRecords(empleados.getTotalElements());
		empleadoGrid.setEmpleadoData(empleados.getContent());

		return empleadoGrid;
	}


}
