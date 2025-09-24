package com.asopagos.novedades.clients;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/obtenerTipoIdentificacionByCasId
 */
public class ObtenerTitularCuentaAdministradorSubsidioByCasId extends ServiceClient {
 
  
  	private String casId;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Object[] result;
  
 	public ObtenerTitularCuentaAdministradorSubsidioByCasId (String casId){
 		super();
		this.casId=casId;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("casId", casId)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		//this.result = (String) response.readEntity(String.class);
		this.result = (Object[]) response.readEntity(Object[].class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Object[] getResult() {
		return result;
	}
 
  	public void setCasId (String casId){
 		this.casId=casId;
 	}
 	
 	public String getCasId (){
 		return casId;
 	}
  
}