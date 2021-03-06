package es.microforum.servicefrontendsoap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import es.microforum.serviceapi.EmpleadoService;

@WebService
public class AumentoSalarioWebService implements IAumentoSalarioWebService{
	private EmpleadoService empleadoService;

	@WebMethod(exclude=true)
	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}
	
	@WebMethod(operationName="callAumentoSalario")
	public void callAumentoSalario(Double porcentaje){
		empleadoService.aumentoSalario(porcentaje);
	}
}
