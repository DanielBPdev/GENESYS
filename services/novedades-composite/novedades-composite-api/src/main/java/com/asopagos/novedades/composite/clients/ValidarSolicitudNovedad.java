package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.composite.dto.VerificarSolicitudNovedadDTO;
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
 * /rest/novedadesComposite/validarSolicitudNovedad
 */
public class ValidarSolicitudNovedad extends ServiceClient { 
    	private VerificarSolicitudNovedadDTO entrada;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public ValidarSolicitudNovedad (VerificarSolicitudNovedadDTO entrada){
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

 
  
  	public void setEntrada (VerificarSolicitudNovedadDTO entrada){
 		this.entrada=entrada;
 	}
 	
 	public VerificarSolicitudNovedadDTO getEntrada (){
 		return entrada;
 	}
  
}