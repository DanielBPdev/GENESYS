package com.asopagos.pila.composite.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PilaComposite/cargarArchivosPilaFtp
 */
public class CargarArchivosPilaFtp extends ServiceClient { 
   	private Long idOperadorInformacion;
   
  
 	public CargarArchivosPilaFtp (Long idOperadorInformacion){
 		super();
		this.idOperadorInformacion=idOperadorInformacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idOperadorInformacion", idOperadorInformacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdOperadorInformacion (Long idOperadorInformacion){
 		this.idOperadorInformacion=idOperadorInformacion;
 	}
 	
 	public Long getIdOperadorInformacion (){
 		return idOperadorInformacion;
 	}
  
  
}