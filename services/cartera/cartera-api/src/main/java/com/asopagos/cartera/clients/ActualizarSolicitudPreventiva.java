package com.asopagos.cartera.clients;

import java.lang.String;
import com.asopagos.dto.modelo.SolicitudPreventivaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/actualizarSolicitudPreventiva/{numeroRadicacion}
 */
public class ActualizarSolicitudPreventiva extends ServiceClient { 
  	private String numeroRadicacion;
    	private SolicitudPreventivaModeloDTO solicitudPreventivaDTO;
  
  
 	public ActualizarSolicitudPreventiva (String numeroRadicacion,SolicitudPreventivaModeloDTO solicitudPreventivaDTO){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.solicitudPreventivaDTO=solicitudPreventivaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudPreventivaDTO == null ? null : Entity.json(solicitudPreventivaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  
  	public void setSolicitudPreventivaDTO (SolicitudPreventivaModeloDTO solicitudPreventivaDTO){
 		this.solicitudPreventivaDTO=solicitudPreventivaDTO;
 	}
 	
 	public SolicitudPreventivaModeloDTO getSolicitudPreventivaDTO (){
 		return solicitudPreventivaDTO;
 	}
  
}