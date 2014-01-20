package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.util.List;
//import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

//import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpresaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-data-app-context.xml"})
@TransactionConfiguration(defaultRollback = true)

public class EmpresaServiceImplTest {

	@Autowired
	EmpresaService empresaService;
	Empresa empresaSave;
	Empresa empresa;
	//Empleado empleado1;
	//Empleado empleado2;
	//Set<Empleado> empleados;
	
	@Before
	public void setUp() throws Exception {
		empresa = new Empresa("1");
		empresa.setNombre("Empresa1");
		empresaSave = new Empresa("125");
		//empleados.add(empleado1);
		//empleados.add(empleado2);
		//empresa.setEmpleados(empleados);
		empresaSave.setNif("125");
		empresaService.save(empresa);
	}

	@Test
	public void testFindAll() {
		List<Empresa> empresas = empresaService.findAll();
		assertTrue(empresas.contains(empresa));
	}
	
	@Test
	public void testFindByNif() {
		empresa = empresaService.findByNif("1");
		assertTrue(empresa.getNif().equals("1"));
	}

	@Test
	@Transactional
	public void testSave() {
		empresa = empresaService.save(empresaSave);
		assertTrue(empresa.getNif().equals("125"));
	}

	@Test
	@Transactional
	public void testDelete() {
		empresaService.delete(empresa);
		List<Empresa> empresas = empresaService.findAll();
		assertTrue(!(empresas.contains(empresa)));
	}
	
	@Test
	public void testFindByNombre(){
		List<Empresa> empresas = empresaService.findByNombre("Empresa1");
		assertTrue(empresas.contains(empresa));
	}
	/*@Test
	public void testFindAllEmpleados() {
		assertTrue(true);
	}*/
	
	@After
	public void tearDown() throws Exception {
		empresaService.delete(empresa);
	}
}
