package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/registroInformacion/aprobacionPrimerNivel/{numeroSolicitud}
 */
public class AprobarLiquidacionMasivaPrimerNivel extends ServiceClient { 
  	private String numeroSolicitud;
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public AprobarLiquidacionMasivaPrimerNivel (String numeroSolicitud){
 		super();
		this.numeroSolicitud=numeroSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroSolicitud", numeroSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 	public void setNumeroSolicitud (String numeroSolicitud){
 		this.numeroSolicitud=numeroSolicitud;
 	}
 	
 	public String getNumeroSolicitud (){
 		return numeroSolicitud;
 	}
  
  
  
}