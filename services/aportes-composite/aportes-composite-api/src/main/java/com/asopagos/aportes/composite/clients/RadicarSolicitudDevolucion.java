package com.asopagos.aportes.composite.clients;

import com.asopagos.aportes.composite.dto.SolicitudDevolucionDTO;
import java.util.Map;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/radicarSolicitudDevolucion
 */
public class RadicarSolicitudDevolucion extends ServiceClient { 
    	private SolicitudDevolucionDTO solicitudDevolucion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public RadicarSolicitudDevolucion (SolicitudDevolucionDTO solicitudDevolucion){
 		super();
		this.solicitudDevolucion=solicitudDevolucion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudDevolucion == null ? null : Entity.json(solicitudDevolucion));
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

 
  
  	public void setSolicitudDevolucion (SolicitudDevolucionDTO solicitudDevolucion){
 		this.solicitudDevolucion=solicitudDevolucion;
 	}
 	
 	public SolicitudDevolucionDTO getSolicitudDevolucion (){
 		return solicitudDevolucion;
 	}
  
}