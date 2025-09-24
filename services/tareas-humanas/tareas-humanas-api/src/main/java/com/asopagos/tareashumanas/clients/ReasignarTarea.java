package com.asopagos.tareashumanas.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/tareasHumanas/{idTarea}/reasignar
 */
public class ReasignarTarea extends ServiceClient { 
  	private Long idTarea;
   	private String usuario;
   
  
 	public ReasignarTarea (Long idTarea,String usuario){
 		super();
		this.idTarea=idTarea;
		this.usuario=usuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTarea", idTarea)
			.queryParam("usuario", usuario)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdTarea (Long idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public Long getIdTarea (){
 		return idTarea;
 	}
  
  	public void setUsuario (String usuario){
 		this.usuario=usuario;
 	}
 	
 	public String getUsuario (){
 		return usuario;
 	}
  
  
}