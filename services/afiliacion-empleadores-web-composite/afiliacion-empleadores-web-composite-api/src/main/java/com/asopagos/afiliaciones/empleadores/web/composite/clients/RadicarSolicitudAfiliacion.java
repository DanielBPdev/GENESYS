package com.asopagos.afiliaciones.empleadores.web.composite.clients;

import com.asopagos.afiliaciones.empleadores.web.composite.dto.RadicarSolicitudAfiliacionDTO;
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
 * /rest/solicitudAfiliacionEmpleador/radicarSolicitudAfiliacion
 */
public class RadicarSolicitudAfiliacion extends ServiceClient { 
    	private RadicarSolicitudAfiliacionDTO entrada;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public RadicarSolicitudAfiliacion (RadicarSolicitudAfiliacionDTO entrada){
 		super();
		this.entrada=entrada;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(entrada == null ? null : Entity.json(entrada));
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

 
  
  	public void setEntrada (RadicarSolicitudAfiliacionDTO entrada){
 		this.entrada=entrada;
 	}
 	
 	public RadicarSolicitudAfiliacionDTO getEntrada (){
 		return entrada;
 	}
  
}