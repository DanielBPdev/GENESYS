package com.asopagos.aportes.composite.clients;

import com.asopagos.aportes.composite.dto.RadicacionAporteManualDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/radicarSolicitudAporte
 */
public class RadicarSolicitudAporte extends ServiceClient { 
    	private RadicacionAporteManualDTO radicacionAporteManualDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public RadicarSolicitudAporte (RadicacionAporteManualDTO radicacionAporteManualDTO){
 		super();
		this.radicacionAporteManualDTO=radicacionAporteManualDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(radicacionAporteManualDTO == null ? null : Entity.json(radicacionAporteManualDTO));
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

 
  
  	public void setRadicacionAporteManualDTO (RadicacionAporteManualDTO radicacionAporteManualDTO){
 		this.radicacionAporteManualDTO=radicacionAporteManualDTO;
 	}
 	
 	public RadicacionAporteManualDTO getRadicacionAporteManualDTO (){
 		return radicacionAporteManualDTO;
 	}
  
}