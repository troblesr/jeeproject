package es.microforum.serviceapi;

import java.util.List;

import es.microforum.model.Empleado;

public interface EmpleadoService {
	
	// Find all empleados
	public List<Empleado> findAll();
	
	// Find a contact with details by id
	public Empleado findById(String dni);
	
	// Insert or update a empleado	
	public Empleado save(Empleado empleado);
	
	// Delete a empleado	
	public void delete(Empleado empleado);
	
	// Find e by nombre
	public List<Empleado> findByNombre(String nombre);
	
	public void deleteAll();
}
