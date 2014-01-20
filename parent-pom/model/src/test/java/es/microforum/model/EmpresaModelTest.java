package es.microforum.model;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;

import es.microforum.model.Empresa;

public class EmpresaModelTest {

	Empresa empresa1;
	Empresa empresa2;
	SimpleDateFormat sdf;
	
	@Before
	public void setUp() throws Exception {
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		empresa1 = new Empresa();
		empresa2 = new Empresa();
		empresa1.setNif("1");
		empresa2.setNif("2");
		empresa1.setDireccionFiscal("direccionFiscal1");
		empresa1.setFechaInicioActividades(sdf.parse("25-05-1995"));
		empresa1.setNombre("nombre");
	}

	@Test
	public void testEqualsObject() {
		assertTrue(!(empresa1.equals(empresa2)));
		assertTrue(empresa1.equals(empresa1));
	}
}
