package com.asopagos.novedades.composite.clients;

import java.lang.String;
import com.asopagos.dto.ResultadoSupervivenciaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/registrarSupervivenciaPersona
 */
public class RegistrarSupervivenciaPersona extends ServiceClient { 
   	private String numeroRadicado;
   	private ResultadoSupervivenciaDTO resulDTO;
  
  
 	public RegistrarSupervivenciaPersona (String numeroRadicado,ResultadoSupervivenciaDTO resulDTO){
 		super();
		this.numeroRadicado=numeroRadicado;
		this.resulDTO=resulDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroRadicado", numeroRadicado)
			.request(MediaType.APPLICATION_JSON)
			.post(resulDTO == null ? null : Entity.json(resulDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
  	public void setResulDTO (ResultadoSupervivenciaDTO resulDTO){
 		this.resulDTO=resulDTO;
 	}
 	
 	public ResultadoSupervivenciaDTO getResulDTO (){
 		return resulDTO;
 	}
  
}