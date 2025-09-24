package com.asopagos.solicitudes.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudes/{idInstanciaProceso}/destinatario
 */
public class ActualizarDestinatarioSolicitud extends ServiceClient { 
  	private String idInstanciaProceso;
   	private String usuario;
   
  
 	public ActualizarDestinatarioSolicitud (String idInstanciaProceso,String usuario){
 		super();
		this.idInstanciaProceso=idInstanciaProceso;
		this.usuario=usuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idInstanciaProceso", idInstanciaProceso)
			.queryParam("usuario", usuario)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdInstanciaProceso (String idInstanciaProceso){
 		this.idInstanciaProceso=idInstanciaProceso;
 	}
 	
 	public String getIdInstanciaProceso (){
 		return idInstanciaProceso;
 	}
  
  	public void setUsuario (String usuario){
 		this.usuario=usuario;
 	}
 	
 	public String getUsuario (){
 		return usuario;
 	}
  
  
}