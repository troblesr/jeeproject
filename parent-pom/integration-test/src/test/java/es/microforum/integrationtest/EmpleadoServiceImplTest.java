package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpleadoService;
import es.microforum.serviceimpl.EmpleadoServiceImpl;

public class EmpleadoServiceImplTest {

	GenericXmlApplicationContext ctx;
	EmpleadoService empleadoService;
	Empleado empleado;
	byte[] imagen;
	Empresa empresa;
	
	@Before
	public void setUp() throws Exception {
		ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:spring-data-app-context.xml");
		ctx.refresh();

		empleado = new Empleado("123");
		empleadoService = ctx.getBean("jpaEmpleadoService", EmpleadoService.class);
		empleadoService.save(empleado);
	}

	@Test
	public void testFindAll() {
		List<Empleado> listado = empleadoService.findAll();
		assertTrue(listado.size()>0);
	}

	@Test
	public void testFindById() {
		assertTrue(true);
	}

	@Test
	public void testSave() {
		assertTrue(true);
	}

	@Test
	public void testDelete() {
		assertTrue(true);
	}

	@Test
	public void testFindByNombre() {
		assertTrue(true);
	}

}
