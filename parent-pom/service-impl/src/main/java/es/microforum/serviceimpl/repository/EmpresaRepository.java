package es.microforum.serviceimpl.repository;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, String> {
	
	public Empresa findByNif(String nif);
	

	@Query("select e from Empleado e where e.nif = :nif")
	public List<Empleado> findAllEmpleados();
}
