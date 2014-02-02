package es.microforum.serviceapi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import es.microforum.model.Empresa;

public interface EmpresaService {
	
	// Find all empresas
	public List<Empresa> findAll();
	
	// Find a empresa by nif
	public Empresa findByNif(String nif);
	
	// Insert or update a empresa	
	public Empresa save(Empresa empresa);
	
	// Delete a empresa	
	public void delete(Empresa empresa);
	
	//find by Nombre
	public List<Empresa> findByNombre(String nombre);
	
	//Find by nombre
	public Page<Empresa> findByNombre(Pageable pageable, String nombre);
	
	//Find by nombre
	public Page<Empresa> findAll(Pageable pageable);
	
	//public List<Empleado> findAllEmpleados();
}
