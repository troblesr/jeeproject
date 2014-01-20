package es.microforum.serviceimplrepository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.microforum.model.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, String> {
	
	public List<Empleado> findByNombre(String nombre);
	
	public Empleado findByDni(String dni);
	
}
