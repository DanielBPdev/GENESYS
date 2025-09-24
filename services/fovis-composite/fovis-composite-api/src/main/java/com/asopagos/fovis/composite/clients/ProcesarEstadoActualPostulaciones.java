package com.asopagos.fovis.composite.clients;

import java.util.List;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/procesarEstadoActualPostulaciones
 */
public class ProcesarEstadoActualPostulaciones extends ServiceClient { 
    	private List<PostulacionFOVISModeloDTO> listaPostulaciones;
  
  
 	public ProcesarEstadoActualPostulaciones (List<PostulacionFOVISModeloDTO> listaPostulaciones){
 		super();
		this.listaPostulaciones=listaPostulaciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaPostulaciones == null ? null : Entity.json(listaPostulaciones));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaPostulaciones (List<PostulacionFOVISModeloDTO> listaPostulaciones){
 		this.listaPostulaciones=listaPostulaciones;
 	}
 	
 	public List<PostulacionFOVISModeloDTO> getListaPostulaciones (){
 		return listaPostulaciones;
 	}
  
}