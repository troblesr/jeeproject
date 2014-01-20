package es.microforum.serviceimplrepository;

//import java.util.List;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


//import es.microforum.model.Empleado;
import es.microforum.model.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, String> {
	
	public Empresa findByNif(String nif);
	
	public List<Empresa> findByNombre(String nombre);
	
	/*@Query("select e from Empleado e where e.nif = :nif")
	public List<Empleado> findAllEmpleados();*/
}
