package es.microforum.serviceimplrepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.microforum.model.Empresa;

public interface EmpresaRepository extends PagingAndSortingRepository<Empresa, String> {
	
	public Empresa findByNif(String nif);
	
	public List<Empresa> findByNombre(String nombre);
	
	public Page<Empresa> findAll(Pageable pageable);
	
	public Page<Empresa> findByNombre(Pageable pageable, String nombre);
	
	/*@Query("select e from Empleado e where e.nif = :nif")
	public List<Empleado> findAllEmpleados();*/
}
