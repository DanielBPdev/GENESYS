package com.asopagos.aportes.composite.clients;

import java.util.Map;
import com.asopagos.aportes.dto.SolicitudCorreccionDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/radicarSolicitudCorreccion
 */
public class RadicarSolicitudCorreccion extends ServiceClient { 
    	private SolicitudCorreccionDTO solicitudCorreccionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public RadicarSolicitudCorreccion (SolicitudCorreccionDTO solicitudCorreccionDTO){
 		super();
		this.solicitudCorreccionDTO=solicitudCorreccionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudCorreccionDTO == null ? null : Entity.json(solicitudCorreccionDTO));
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

 
  
  	public void setSolicitudCorreccionDTO (SolicitudCorreccionDTO solicitudCorreccionDTO){
 		this.solicitudCorreccionDTO=solicitudCorreccionDTO;
 	}
 	
 	public SolicitudCorreccionDTO getSolicitudCorreccionDTO (){
 		return solicitudCorreccionDTO;
 	}
  
}