package com.asopagos.aportes.clients;

import com.asopagos.aportes.dto.GeneracionArchivoCierreDTO;
import com.asopagos.dto.modelo.SolicitudCierreRecaudoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/generarArchivoCierreRecaudo
 */
public class GenerarArchivoCierreRecaudo extends ServiceClient { 
    	private GeneracionArchivoCierreDTO generacionArchivo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudCierreRecaudoModeloDTO result;
  
 	public GenerarArchivoCierreRecaudo (GeneracionArchivoCierreDTO generacionArchivo){
 		super(); 
		this.generacionArchivo=generacionArchivo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {

		System.out.println("Path ->  GenerarArchivoCierreRecaudo "+path);
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(generacionArchivo == null ? null : Entity.json(generacionArchivo));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudCierreRecaudoModeloDTO) response.readEntity(SolicitudCierreRecaudoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudCierreRecaudoModeloDTO getResult() {
		return result;
	}

 
  
  	public void setGeneracionArchivo (GeneracionArchivoCierreDTO generacionArchivo){
 		this.generacionArchivo=generacionArchivo;
 	}
 	
 	public GeneracionArchivoCierreDTO getGeneracionArchivo (){
 		return generacionArchivo;
 	}
  
}