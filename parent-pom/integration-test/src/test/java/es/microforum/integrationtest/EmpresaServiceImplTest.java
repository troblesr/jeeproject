package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpresaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-data-app-context.xml")
@TransactionConfiguration(defaultRollback = true)

public class EmpresaServiceImplTest {

	@Autowired
	EmpresaService empresaService;
	Empresa empresaSave;
	Empresa empresa;
	Empleado empleado1;
	Empleado empleado2;
	Set<Empleado> empleados;
	
	@Before
	public void setUp() throws Exception {
		empresa = new Empresa("1");
		empleados.add(empleado1);
		empleados.add(empleado2);
		empresa.setEmpleados(empleados);
		empresaSave.setNif("125");
		empresaService.save(empresa);
	}

	@Test
	public void testFindAll() {
		List<Empresa> empresas;
	}

	@Test
	public void testFindByNif() {
		assertTrue(true);
	}

	@Test
	@Transactional
	public void testSave() {
		assertTrue(true);
	}

	@Test
	@Transactional
	public void testDelete() {
		assertTrue(true);
	}

	@Test
	public void testFindAllEmpleados() {
		assertTrue(true);
	}
	
	@After
	public void tearDown() throws Exception {
		empresaService.delete(empresa);
	}
}
