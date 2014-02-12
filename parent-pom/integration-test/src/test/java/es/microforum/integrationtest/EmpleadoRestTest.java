package es.microforum.integrationtest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import es.microforum.model.Empleado;
import es.microforum.model.Empresa;
import es.microforum.serviceapi.EmpleadoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-data-app-context.xml"})
@TransactionConfiguration(defaultRollback = true)

public class EmpleadoRestTest {

	RestTemplate restTemplate = new RestTemplate();

    @Autowired     
    EmpleadoService empleadoService;
    
	@Autowired
	ApplicationContext context;
    
    Empleado empleadoSaveDelete;
    URI uri;
	String acceptHeaderValue;
    String jpaWebContext = "http://localhost:8081/service-frontend/";
    
	private JdbcTemplate jdbcTemplate;
    
    byte[] imagen;
    Empresa empresa;
    
    @Before
    public void setUp() throws Exception {
		DataSource dataSource = (DataSource) context.getBean("dataSource");
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("INSERT INTO empresa values('9999','empresa','direccion','2014-01-28',0)");
		jdbcTemplate.execute("INSERT INTO empleado values('9999','nombre','BORRAR','BORRAR','BORRAR',1000.0,1.0,1000,null,9999,0)");
		uri = new URI(jpaWebContext+"empleado/9999");
		acceptHeaderValue = "application/json";
    }
	
	@Test
	public void getTest() {
		try {
			Resource<Empleado> resource = getEmpleado(uri);
			assertTrue(resource.getContent().getNombre().equals("nombre"));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	

	private Resource<Empleado> getEmpleado(URI uri) {
		return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Empleado>>() {
		}).getBody();

	}
	
	@Test
	public void deleteTest() {
		try {
			restTemplate.delete(jpaWebContext + "empleado/9999");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(empleadoService.findById("9999")==null);
	}
	
	@Test
	public void saveTest() throws RestClientException, URISyntaxException {
		jdbcTemplate.execute("DELETE FROM empleado where dni ='9999'");
		
		String url = jpaWebContext+"empleado";
		
		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.valueOf(acceptHeaderValue));
		requestHeaders.setAccept(mediaTypes);
		requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue));
		
		HttpMethod post = HttpMethod.POST;
		
		String body = "{\"dni\":\"9999\",\"nombre\":\"nombre\",\"tipoEmpleado\":\"BORRAR\",\"empleadocol\":\"BORRAR\",\"salarioAnual\":1000.0,\"valorHora\":1.0,\"cantidadHoras\":1000,\"imagen\":null,\"empresa\":{\"nif\":9999,\"version\":0},\"version\":0}";
		HttpEntity<String> entity = new HttpEntity<String>(body, requestHeaders);

		ResponseEntity<String> response = restTemplate.exchange(url, post, entity, String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
		}
	
    
    @Test
	public void modificarTest() throws RestClientException, URISyntaxException {		
		String url = jpaWebContext + "empleado/9999";
		String acceptHeaderValue = "application/json";

		HttpHeaders requestHeaders = new HttpHeaders();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.valueOf(acceptHeaderValue));
		requestHeaders.setAccept(mediaTypes);
		requestHeaders.setContentType(MediaType.valueOf(acceptHeaderValue));
		HttpMethod put = HttpMethod.PUT;

		String body = "{\"nombre\":\"nombreModificado!\"}";
		HttpEntity<String> entity = new HttpEntity<String>(body, requestHeaders);

		ResponseEntity<String> response = restTemplate.exchange(url, put, entity, String.class);
		assertTrue(response.getStatusCode().equals(HttpStatus.NO_CONTENT));
	}
    
	@After
	public void after() {
		jdbcTemplate.execute("DELETE FROM empleado where dni=9999");
		jdbcTemplate.execute("DELETE FROM empresa where nif=9999");
	}

}
