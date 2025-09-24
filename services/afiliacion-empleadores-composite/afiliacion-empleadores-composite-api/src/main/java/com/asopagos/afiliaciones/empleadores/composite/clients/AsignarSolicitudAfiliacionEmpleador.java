package com.asopagos.afiliaciones.empleadores.composite.clients;

import com.asopagos.afiliaciones.empleadores.composite.dto.AsignarSolicitudAfiliacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/asignarSolicitudAfiliacion
 */
public class AsignarSolicitudAfiliacionEmpleador extends ServiceClient { 
    	private AsignarSolicitudAfiliacionDTO inDTO;
  
  
 	public AsignarSolicitudAfiliacionEmpleador (AsignarSolicitudAfiliacionDTO inDTO){
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
	

 
  
  	public void setInDTO (AsignarSolicitudAfiliacionDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public AsignarSolicitudAfiliacionDTO getInDTO (){
 		return inDTO;
 	}
  
}