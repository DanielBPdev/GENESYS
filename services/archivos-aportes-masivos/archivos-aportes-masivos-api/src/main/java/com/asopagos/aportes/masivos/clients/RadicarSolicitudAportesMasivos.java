package com.asopagos.aportes.masivos.clients;

import com.asopagos.aportes.composite.dto.SolicitudDevolucionDTO;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import java.util.Map;
import java.lang.String;
import java.util.List;
import java.util.ArrayList;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/radicarSolicitudAportesMasivos
 */
public class RadicarSolicitudAportesMasivos extends ServiceClient { 
    	private SolicitudModeloDTO solicitud;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public RadicarSolicitudAportesMasivos (SolicitudModeloDTO solicitud){
 		super();
		this.solicitud=solicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitud == null ? null : Entity.json(solicitud));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,String> getResult() {
		return result;
	}

 
  
  	public void setSolicitud (SolicitudModeloDTO solicitud){
 		this.solicitud=solicitud;
 	}
 	
 	public SolicitudModeloDTO getSolicitud(){
 		return solicitud;
 	}
  
}