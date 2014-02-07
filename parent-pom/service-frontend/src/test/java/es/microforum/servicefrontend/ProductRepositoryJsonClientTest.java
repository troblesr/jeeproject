package es.microforum.servicefrontend;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URI;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import es.microforum.model.Empleado;


public class ProductRepositoryJsonClientTest {

	RestTemplate restTemplate = new RestTemplate();

	@Test
	public void getTest() {
		try {
			Resource<Empleado> resource = getEmpleado(new URI("http://localhost:8081/service-frontend-0.0.3-SNAPSHOT/empleado/1"));
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


}
