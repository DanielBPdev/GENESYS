package com.asopagos.comunicados.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/certificados/enviar
 */
public class RegistrarEnvioCertificado extends ServiceClient { 
   	private Long idComunicado;
  	private Long idCertificado;
   
  
 	public RegistrarEnvioCertificado (Long idComunicado,Long idCertificado){
 		super();
		this.idComunicado=idComunicado;
		this.idCertificado=idCertificado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idComunicado", idComunicado)
			.queryParam("idCertificado", idCertificado)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdComunicado (Long idComunicado){
 		this.idComunicado=idComunicado;
 	}
 	
 	public Long getIdComunicado (){
 		return idComunicado;
 	}
  	public void setIdCertificado (Long idCertificado){
 		this.idCertificado=idCertificado;
 	}
 	
 	public Long getIdCertificado (){
 		return idCertificado;
 	}
  
  
}