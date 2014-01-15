package es.microforum.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import es.microforum.model.Empleado;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.serviceimpl.repository.EmpleadoRepository;


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

}
