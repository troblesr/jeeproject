package es.microforum.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;


public class EmpleadoModelTest {
	
	Empleado empleado;
	Empleado empleado2;
	Empleado empleado3;
	Empresa empresa;
	byte[] imagen;
	
	@Before
	public void setUp() throws Exception {
		empleado = new Empleado();
		empleado2 = new Empleado();
		empleado.setDni("1");
		empleado2.setDni("2");
		imagen = new byte[0];
		empleado3 = new Empleado("3", empresa, "nombre3", "direccion3", "tipoEmpleado3", "tipoEmpleado3", 30000.0, 20.0, 1500.0, imagen);
	}

	@Test
	public void testEqualsObject() {
		assertTrue(!(empleado.equals(empleado2)));
		assertTrue(empleado.equals(empleado));
		assertTrue(empleado3.equals(empleado3));
	}
}
