package com.asopagos.fovis.clients;

import java.lang.String;
import com.asopagos.dto.modelo.SolicitudModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/consultarSolicitud/{numeroRadicado}
 */
public class ConsultarSolicitudPorRadicado extends ServiceClient { 
  	private String numeroRadicado;
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudModeloDTO result;
  
 	public ConsultarSolicitudPorRadicado (String numeroRadicado){
 		super();
		this.numeroRadicado=numeroRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicado", numeroRadicado)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudModeloDTO) response.readEntity(SolicitudModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudModeloDTO getResult() {
		return result;
	}

 	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
  
  
}