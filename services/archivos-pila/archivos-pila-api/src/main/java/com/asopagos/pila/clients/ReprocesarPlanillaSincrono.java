package com.asopagos.pila.clients;

import com.asopagos.dto.ArchivoPilaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/reprocesarPlanillaOISincrono
 */
public class ReprocesarPlanillaSincrono extends ServiceClient { 
    	private ArchivoPilaDTO archivoPilaDTO;
  
  
 	public ReprocesarPlanillaSincrono (ArchivoPilaDTO archivoPilaDTO){
 		super();
		this.archivoPilaDTO=archivoPilaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(archivoPilaDTO == null ? null : Entity.json(archivoPilaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setArchivoPilaDTO (ArchivoPilaDTO archivoPilaDTO){
 		this.archivoPilaDTO=archivoPilaDTO;
 	}
 	
 	public ArchivoPilaDTO getArchivoPilaDTO (){
 		return archivoPilaDTO;
 	}
  
}