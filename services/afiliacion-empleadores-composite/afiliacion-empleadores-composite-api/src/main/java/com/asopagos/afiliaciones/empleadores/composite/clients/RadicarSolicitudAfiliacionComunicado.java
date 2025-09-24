package com.asopagos.afiliaciones.empleadores.composite.clients;

import com.asopagos.afiliaciones.empleadores.composite.dto.RadicarSolicitudAfiliacionDTO;
import java.util.Map;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/radicarSolicitudAfiliacionComunicado
 */
public class RadicarSolicitudAfiliacionComunicado extends ServiceClient { 
    	private RadicarSolicitudAfiliacionDTO inDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public RadicarSolicitudAfiliacionComunicado (RadicarSolicitudAfiliacionDTO inDTO){
 		super();
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,Object>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,Object> getResult() {
		return result;
	}

 
  
  	public void setInDTO (RadicarSolicitudAfiliacionDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public RadicarSolicitudAfiliacionDTO getInDTO (){
 		return inDTO;
 	}
  
}