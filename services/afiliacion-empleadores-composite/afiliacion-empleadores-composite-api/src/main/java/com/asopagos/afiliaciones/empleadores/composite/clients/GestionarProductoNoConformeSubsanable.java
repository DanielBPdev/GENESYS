package com.asopagos.afiliaciones.empleadores.composite.clients;

import com.asopagos.afiliaciones.empleadores.composite.dto.GestionarProductoNoConformeSubsanableDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/gestionarProductoNoConformeSubsanable
 */
public class GestionarProductoNoConformeSubsanable extends ServiceClient { 
    	private GestionarProductoNoConformeSubsanableDTO inDTO;
  
  
 	public GestionarProductoNoConformeSubsanable (GestionarProductoNoConformeSubsanableDTO inDTO){
 		super();
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setInDTO (GestionarProductoNoConformeSubsanableDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public GestionarProductoNoConformeSubsanableDTO getInDTO (){
 		return inDTO;
 	}
  
}