package es.microforum.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.serviceimplrepository.EmpleadoRepository;

@Service("jpaEmpleadoService")
@Repository
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

	@Autowired
	private EmpleadoRepository empleadoRepository;
	
	@Transactional(readOnly=true)
	public List<Empleado> findAll() {
		return Lists.newArrayList(empleadoRepository.findAll());
	}

	@Transactional(readOnly=true)	
	public Empleado findById(String dni) {
		return empleadoRepository.findByDni(dni);		
	}

	public Empleado save(Empleado empleado) {
		return empleadoRepository.save(empleado);
	}

	public void delete(Empleado empleado) {
		empleadoRepository.delete(empleado);
	}

	@Transactional(readOnly=true)
	public List<Empleado> findByNombre(String nombre) {
		return empleadoRepository.findByNombre(nombre);
	}

	@Transactional(readOnly=true)
	public Page<Empleado> findByNombre(Pageable pageable, String nombre) {
		return empleadoRepository.findByNombre(pageable,nombre);
	}
	
	@Transactional(readOnly=true)
	public Page<Empleado> findAll(Pageable pageable){
		return empleadoRepository.findAll(pageable);
	}
	
	public void deleteAll() {
		empleadoRepository.deleteAll();	
	}

	@Transactional(readOnly=true)
	public Empleado findByIdWithEmpresa(String dni) {
		Empleado empleado = empleadoRepository.findByDni(dni);
		Empresa empresa = empleado.getEmpresa();
		empresa.getEmpleados().size();
		return empleado;
	}
}