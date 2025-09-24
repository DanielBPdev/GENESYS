package com.asopagos.bandejainconsistencias.clients;

import java.lang.Long;
import com.asopagos.entidades.pila.staging.RegistroGeneral;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pilaBandeja/consultarRegistroGeneralxRegistroControl
 */
public class ConsultarRegistroGeneralxRegistroControl extends ServiceClient {
 
  
  	private Long indicePlanilla;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RegistroGeneral result;
  
 	public ConsultarRegistroGeneralxRegistroControl (Long indicePlanilla){
 		super();
		this.indicePlanilla=indicePlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("indicePlanilla", indicePlanilla)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RegistroGeneral) response.readEntity(RegistroGeneral.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RegistroGeneral getResult() {
		return result;
	}

 
  	public void setIndicePlanilla (Long indicePlanilla){
 		this.indicePlanilla=indicePlanilla;
 	}
 	
 	public Long getIndicePlanilla (){
 		return indicePlanilla;
 	}
  
}