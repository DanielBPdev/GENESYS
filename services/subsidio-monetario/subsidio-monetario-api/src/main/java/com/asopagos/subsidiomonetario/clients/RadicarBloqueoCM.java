package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/radicar/bloqueoCM/{idCargueBloqueoCuotaMonetaria}
 */
public class RadicarBloqueoCM extends ServiceClient { 
  	private Long idCargueBloqueoCuotaMonetaria;
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private int result;
  
 	public RadicarBloqueoCM (Long idCargueBloqueoCuotaMonetaria){
 		super();
		this.idCargueBloqueoCuotaMonetaria=idCargueBloqueoCuotaMonetaria;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idCargueBloqueoCuotaMonetaria", idCargueBloqueoCuotaMonetaria)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (int) response.readEntity(int.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public int getResult() {
		return result;
	}

 	public void setIdCargueBloqueoCuotaMonetaria (Long idCargueBloqueoCuotaMonetaria){
 		this.idCargueBloqueoCuotaMonetaria=idCargueBloqueoCuotaMonetaria;
 	}
 	
 	public Long getIdCargueBloqueoCuotaMonetaria (){
 		return idCargueBloqueoCuotaMonetaria;
 	}
  
  
  
}