package es.microforum.serviceapi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import es.microforum.model.Empleado;

public interface EmpleadoService {
	
	// Find all empleados
	public List<Empleado> findAll();
	
	// Find a contact with details by id
	public Empleado findById(String dni);
	
	public Empleado findByIdWithEmpresa(String dni);
	
	// Insert or update a empleado	
	public Empleado save(Empleado empleado);
	
	// Delete a empleado	
	public void delete(Empleado empleado);
	
	// Find e by nombre
	public List<Empleado> findByNombre(String nombre);
	
	//Find by nombre
	public Page<Empleado> findByNombre(Pageable pageable, String nombre);
	
	//Find by nombre
	public Page<Empleado> findAll(Pageable pageable);
}
