package com.asopagos.archivos.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivos/eliminarArchivo/{identificadorArchivo}
 */
public class EliminarVersionArchivo extends ServiceClient { 
  	private String identificadorArchivo;
   	private String versionDocumento;
   
  
 	public EliminarVersionArchivo (String identificadorArchivo,String versionDocumento){
 		super();
		this.identificadorArchivo=identificadorArchivo;
		this.versionDocumento=versionDocumento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("identificadorArchivo", identificadorArchivo)
			.queryParam("versionDocumento", versionDocumento)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdentificadorArchivo (String identificadorArchivo){
 		this.identificadorArchivo=identificadorArchivo;
 	}
 	
 	public String getIdentificadorArchivo (){
 		return identificadorArchivo;
 	}
  
  	public void setVersionDocumento (String versionDocumento){
 		this.versionDocumento=versionDocumento;
 	}
 	
 	public String getVersionDocumento (){
 		return versionDocumento;
 	}
  
  
}