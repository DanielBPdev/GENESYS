package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/actualizarRegistroSolicitudAnibol/{idRegistroSolicitudAnibol}
 */
public class ActualizarRegistroSolicitudAnibol extends ServiceClient { 
  	private Long idRegistroSolicitudAnibol;
    	private String parametrosOUT;
  
  
 	public ActualizarRegistroSolicitudAnibol (Long idRegistroSolicitudAnibol,String parametrosOUT){
 		super();
		this.idRegistroSolicitudAnibol=idRegistroSolicitudAnibol;
		this.parametrosOUT=parametrosOUT;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idRegistroSolicitudAnibol", idRegistroSolicitudAnibol)
			.request(MediaType.APPLICATION_JSON)
			.put(parametrosOUT == null ? null : Entity.json(parametrosOUT));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdRegistroSolicitudAnibol (Long idRegistroSolicitudAnibol){
 		this.idRegistroSolicitudAnibol=idRegistroSolicitudAnibol;
 	}
 	
 	public Long getIdRegistroSolicitudAnibol (){
 		return idRegistroSolicitudAnibol;
 	}
  
  
  	public void setParametrosOUT (String parametrosOUT){
 		this.parametrosOUT=parametrosOUT;
 	}
 	
 	public String getParametrosOUT (){
 		return parametrosOUT;
 	}
  
}