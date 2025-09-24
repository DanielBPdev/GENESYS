package com.asopagos.afiliaciones.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/etiquetas/{codigoEtiqueta}
 */
public class ValidarEtiqueta extends ServiceClient { 
  	private String codigoEtiqueta;
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ValidarEtiqueta (String codigoEtiqueta){
 		super();
		this.codigoEtiqueta=codigoEtiqueta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("codigoEtiqueta", codigoEtiqueta)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public String getResult() {
		return result;
	}

 	public void setCodigoEtiqueta (String codigoEtiqueta){
 		this.codigoEtiqueta=codigoEtiqueta;
 	}
 	
 	public String getCodigoEtiqueta (){
 		return codigoEtiqueta;
 	}
  
  
  
}