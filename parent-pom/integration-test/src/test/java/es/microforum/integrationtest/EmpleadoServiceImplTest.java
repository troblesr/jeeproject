package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.util.List;

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
import es.microforum.serviceapi.EmpleadoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-data-app-context.xml")
@TransactionConfiguration(defaultRollback = true)

public class EmpleadoServiceImplTest {

	@Autowired
	EmpleadoService empleadoService;
	Empleado empleadoSaveDelete;
	Empleado empleado;
	byte[] imagen;
	Empresa empresa;
	
	@Before
	public void setUp() throws Exception {

		empleado = new Empleado("1");
		empleado.setNombre("nombre");
		empleadoSaveDelete = new Empleado("125");
		empleadoService.save(empleado);
	}

	@Test
	public void testFindAll() {
		List<Empleado> listado = empleadoService.findAll();
		assertTrue(listado.contains(empleado));
	}

	@Test
	public void testFindById() {
		empleado = empleadoService.findById("1");
		assertTrue(empleado.getDni().equals("1"));
	}

	@Test
	@Transactional
	public void testSave() {
		empleado = empleadoService.save(empleadoSaveDelete);
		assertTrue(empleado.getDni().equals("125"));
	}

	@Test
	@Transactional
	public void testDelete() {
		empleadoService.delete(empleado);
		List<Empleado> listado = empleadoService.findAll();
		assertTrue(!(listado.contains(empleado)));		
	}

	@Test
	public void testFindByNombre() {		
		List<Empleado> listado = empleadoService.findByNombre("nombre");
		assertTrue(listado.contains(empleado));
	}
	
	@After
	public void tearDown() throws Exception {
		empleadoService.delete(empleado);
	}
	

}
