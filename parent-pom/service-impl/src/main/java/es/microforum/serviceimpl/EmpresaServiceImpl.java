package es.microforum.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpresaService;
import es.microforum.serviceimpl.repository.EmpresaRepository;

public class EmpresaServiceImpl implements EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Transactional(readOnly=true)
	public List<Empresa> findAll() {
		return Lists.newArrayList(empresaRepository.findAll());
	}

	@Transactional(readOnly=true)	
	public Empresa findByNif(String nif) {		
		return empresaRepository.findByNif(nif);
	}

	public Empresa save(Empresa empresa) {
		return null;
	}

	public void delete(Empresa empresa) {}

	@Override
	public List<Empleado> findAllEmpleados() {
		return null;
	}

}
