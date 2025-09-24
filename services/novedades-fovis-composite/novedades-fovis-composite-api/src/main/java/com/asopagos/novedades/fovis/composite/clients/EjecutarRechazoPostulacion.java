package com.asopagos.novedades.fovis.composite.clients;

import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesConvertidorFovisComposite/ejecutarRechazoPostulacion
 */
public class EjecutarRechazoPostulacion extends ServiceClient { 
    	private PostulacionFOVISModeloDTO postulacion;
  
  
 	public EjecutarRechazoPostulacion (PostulacionFOVISModeloDTO postulacion){
 		super();
		this.postulacion=postulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(postulacion == null ? null : Entity.json(postulacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setPostulacion (PostulacionFOVISModeloDTO postulacion){
 		this.postulacion=postulacion;
 	}
 	
 	public PostulacionFOVISModeloDTO getPostulacion (){
 		return postulacion;
 	}
  
}