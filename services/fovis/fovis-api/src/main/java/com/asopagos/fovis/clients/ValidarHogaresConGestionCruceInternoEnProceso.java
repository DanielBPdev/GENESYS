package com.asopagos.fovis.clients;

import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/validarHogaresConGestionCruceInternoEnProceso
 */
public class ValidarHogaresConGestionCruceInternoEnProceso extends ServiceClient {
 
  
  	private String nombreCicloAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarHogaresConGestionCruceInternoEnProceso (String nombreCicloAsignacion){
 		super();
		this.nombreCicloAsignacion=nombreCicloAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nombreCicloAsignacion", nombreCicloAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Boolean getResult() {
		return result;
	}

 
  	public void setNombreCicloAsignacion (String nombreCicloAsignacion){
 		this.nombreCicloAsignacion=nombreCicloAsignacion;
 	}
 	
 	public String getNombreCicloAsignacion (){
 		return nombreCicloAsignacion;
 	}
  
}