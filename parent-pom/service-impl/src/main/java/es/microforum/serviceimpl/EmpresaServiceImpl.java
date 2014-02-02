package es.microforum.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpresaService;
import es.microforum.serviceimplrepository.EmpresaRepository;

@Service("jpaEmpresaService")
@Repository
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Transactional(readOnly=true)
	public List<Empresa> findAll() {
		return Lists.newArrayList(empresaRepository.findAll());
	}

	@Transactional(readOnly=true)	
	public Empresa findByNif(String nif) {		
		return empresaRepository.findByNif(nif);
	}

	public Empresa save(Empresa empresa) {
		return empresaRepository.save(empresa);
	}

	public void delete(Empresa empresa) {
		empresaRepository.delete(empresa);
	}

	public List<Empresa> findByNombre(String nombre) {
		return empresaRepository.findByNombre(nombre);
	}

	@Transactional(readOnly=true)
	public Page<Empresa> findByNombre(Pageable pageable, String nombre) {
		return empresaRepository.findByNombre(pageable, nombre);
	}

	@Transactional(readOnly=true)
	public Page<Empresa> findAll(Pageable pageable) {
		return empresaRepository.findAll(pageable);
	}
	
	/*public List<Empleado> findAllEmpleados(){
		return empresaRepository.findAllEmpleados();
	}*/

}