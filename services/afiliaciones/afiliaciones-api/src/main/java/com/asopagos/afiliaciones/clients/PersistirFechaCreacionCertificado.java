package com.asopagos.afiliaciones.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/persistirFechaCreacionCertificado
 */
public class PersistirFechaCreacionCertificado extends ServiceClient { 
   	private Long idCertificado;
   
  
 	public PersistirFechaCreacionCertificado (Long idCertificado){
 		super();
		this.idCertificado=idCertificado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idCertificado", idCertificado)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdCertificado (Long idCertificado){
 		this.idCertificado=idCertificado;
 	}
 	
 	public Long getIdCertificado (){
 		return idCertificado;
 	}
  
  
}