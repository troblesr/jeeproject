package es.microforum.serviceimpl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;

public interface EmpresaRepository extends CrudRepository<Empresa, String> {
	
	public Empresa findByNif(String nif);
	
	//public List<Empleado> findAllEmpleados();
}
