package es.microforum.integrationtest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpleadoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-data-app-context.xml"})
@TransactionConfiguration(defaultRollback = true)

public class EmpleadoServiceImplTest {

		private static final Logger logger = LoggerFactory.getLogger(EmpleadoServiceImplTest.class);
	
        @Autowired     
        EmpleadoService empleadoService;
        
        Empleado empleadoSaveDelete;
        Empleado empleado;
        Empleado ampliarSueldo;
        
        byte[] imagen;
        Empresa empresa;
        
        Double porcentaje;
        
        @Before
        public void setUp() throws Exception {
                empleado = new Empleado("9999");
                empleado.setNombre("nombre");
                empleado.setSalarioAnual(20000.0);
                porcentaje = 5.0; 
                empleado.setVersion(0);
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
                empleado = empleadoService.findById("9999");
                assertTrue(empleado.getDni().equals("9999"));
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
        @Test
        @Transactional
        public void testAumentoSalario() {
        	//Borramos el empleado de prueba del resto de test ya que produce un StaleObjectException
        	empleadoService.delete(empleado);
        	
        	ampliarSueldo = new Empleado("8888");
        	ampliarSueldo.setSalarioAnual(20000.0);
        	ampliarSueldo.setVersion(0);        	
            empleadoService.save(ampliarSueldo);
            
            empleadoService.aumentoSalario(porcentaje);
            assertTrue(empleadoService.findById("8888").getSalarioAnual().equals(21000.0));
        }
        
        @After
        public void tearDown() throws Exception {
                empleadoService.delete(empleado);
        }
        

}