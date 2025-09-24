package com.asopagos.asignaciones.clients;

import com.asopagos.asignaciones.dto.InicioTareaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/asignacionSolicitud/inicioTarea
 */
public class RegistrarInicioTarea extends ServiceClient { 
    	private InicioTareaDTO inicioTareaDTO;
  
  
 	public RegistrarInicioTarea (InicioTareaDTO inicioTareaDTO){
 		super();
		this.inicioTareaDTO=inicioTareaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inicioTareaDTO == null ? null : Entity.json(inicioTareaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setInicioTareaDTO (InicioTareaDTO inicioTareaDTO){
 		this.inicioTareaDTO=inicioTareaDTO;
 	}
 	
 	public InicioTareaDTO getInicioTareaDTO (){
 		return inicioTareaDTO;
 	}
  
}