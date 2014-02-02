package es.microforum.serviceimplrepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.microforum.model.Empleado;

public interface EmpleadoRepository extends PagingAndSortingRepository<Empleado, String> {
	
	public List<Empleado> findByNombre(String nombre);
	
	public Empleado findByDni(String dni);
	
	public Page<Empleado> findByNombre(Pageable pageable, String nombre);
	
	public Page<Empleado> findAll(Pageable pageable);
}
