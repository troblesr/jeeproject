package es.microforum.servicefrontendsoap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IAumentoSalarioWebService {
	@WebMethod(operationName = "callAumento")
	public void callAumento(Double porcentaje);
}
