package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.ArchivoConsumosAnibolModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/guardarRegistroArchivoConsumosAnibol
 */
public class GuardarRegistroArchivoConsumosAnibol extends ServiceClient { 
    	private ArchivoConsumosAnibolModeloDTO archivoConsumosAnibolModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public GuardarRegistroArchivoConsumosAnibol (ArchivoConsumosAnibolModeloDTO archivoConsumosAnibolModeloDTO){
 		super();
		this.archivoConsumosAnibolModeloDTO=archivoConsumosAnibolModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(archivoConsumosAnibolModeloDTO == null ? null : Entity.json(archivoConsumosAnibolModeloDTO));
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

 
  
  	public void setArchivoConsumosAnibolModeloDTO (ArchivoConsumosAnibolModeloDTO archivoConsumosAnibolModeloDTO){
 		this.archivoConsumosAnibolModeloDTO=archivoConsumosAnibolModeloDTO;
 	}
 	
 	public ArchivoConsumosAnibolModeloDTO getArchivoConsumosAnibolModeloDTO (){
 		return archivoConsumosAnibolModeloDTO;
 	}
  
}