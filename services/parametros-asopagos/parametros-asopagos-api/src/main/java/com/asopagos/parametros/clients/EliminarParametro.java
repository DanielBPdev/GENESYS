package com.asopagos.parametros.clients;

import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio DELETE
 * /rest/parametros/admon/{nombreTabla}/{idEntidad}
 */
public class EliminarParametro extends ServiceClient {
 
  	private String idEntidad;
  	private String nombreTabla;
  
  
  
 	public EliminarParametro (String idEntidad,String nombreTabla){
 		super();
		this.idEntidad=idEntidad;
		this.nombreTabla=nombreTabla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEntidad", idEntidad)
						.resolveTemplate("nombreTabla", nombreTabla)
									.request(MediaType.APPLICATION_JSON).delete();
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdEntidad (String idEntidad){
 		this.idEntidad=idEntidad;
 	}
 	
 	public String getIdEntidad (){
 		return idEntidad;
 	}
  	public void setNombreTabla (String nombreTabla){
 		this.nombreTabla=nombreTabla;
 	}
 	
 	public String getNombreTabla (){
 		return nombreTabla;
 	}
  
  
}