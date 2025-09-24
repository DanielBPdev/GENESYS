package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import com.asopagos.entidades.subsidiomonetario.pagos.ArchivoTransDetaSubsidio;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/persistirArchivoTransDetaSubsidio
 */
public class PersistirArchivoTransDetaSubsidio extends ServiceClient { 
    	private ArchivoTransDetaSubsidio archivoTransDetaSubsidio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public PersistirArchivoTransDetaSubsidio (ArchivoTransDetaSubsidio archivoTransDetaSubsidio){
 		super();
		this.archivoTransDetaSubsidio=archivoTransDetaSubsidio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(archivoTransDetaSubsidio == null ? null : Entity.json(archivoTransDetaSubsidio));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setArchivoTransDetaSubsidio (ArchivoTransDetaSubsidio archivoTransDetaSubsidio){
 		this.archivoTransDetaSubsidio=archivoTransDetaSubsidio;
 	}
 	
 	public ArchivoTransDetaSubsidio getArchivoTransDetaSubsidio (){
 		return archivoTransDetaSubsidio;
 	}
  
}