package com.asopagos.afiliados.clients;

import com.asopagos.afiliados.dto.ActivacionAfiliadoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/activarAfiliado
 */
public class ActivarAfiliado extends ServiceClient { 
    	private ActivacionAfiliadoDTO datosActivacion;
  
  
 	public ActivarAfiliado (ActivacionAfiliadoDTO datosActivacion){
 		super();
		this.datosActivacion=datosActivacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosActivacion == null ? null : Entity.json(datosActivacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDatosActivacion (ActivacionAfiliadoDTO datosActivacion){
 		this.datosActivacion=datosActivacion;
 	}
 	
 	public ActivacionAfiliadoDTO getDatosActivacion (){
 		return datosActivacion;
 	}
  
}