package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import com.asopagos.aportes.composite.dto.GestionInformacionFaltanteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/{idSolicitud}/finalizarInformacionfaltante
 */
public class FinalizarInformacionfaltante extends ServiceClient { 
  	private Long idSolicitud;
    	private GestionInformacionFaltanteDTO informacionFaltante;
  
  
 	public FinalizarInformacionfaltante (Long idSolicitud,GestionInformacionFaltanteDTO informacionFaltante){
 		super();
		this.idSolicitud=idSolicitud;
		this.informacionFaltante=informacionFaltante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitud", idSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(informacionFaltante == null ? null : Entity.json(informacionFaltante));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  
  	public void setInformacionFaltante (GestionInformacionFaltanteDTO informacionFaltante){
 		this.informacionFaltante=informacionFaltante;
 	}
 	
 	public GestionInformacionFaltanteDTO getInformacionFaltante (){
 		return informacionFaltante;
 	}
  
}