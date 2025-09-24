package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.dto.ArchivoLiquidacionSubsidioModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/archivosLiquidacion/gestionar
 */
public class GestionarArchivosLiquidacion extends ServiceClient { 
    	private ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public GestionarArchivosLiquidacion (ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO){
 		super();
		this.archivoLiquidacionDTO=archivoLiquidacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(archivoLiquidacionDTO == null ? null : Entity.json(archivoLiquidacionDTO));
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

 
  
  	public void setArchivoLiquidacionDTO (ArchivoLiquidacionSubsidioModeloDTO archivoLiquidacionDTO){
 		this.archivoLiquidacionDTO=archivoLiquidacionDTO;
 	}
 	
 	public ArchivoLiquidacionSubsidioModeloDTO getArchivoLiquidacionDTO (){
 		return archivoLiquidacionDTO;
 	}
  
}