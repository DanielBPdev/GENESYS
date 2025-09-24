package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/actualizarDetalleSubsidioAsignado
 */
public class ActualizarDetalleSubsidioAsignado extends ServiceClient { 
    	private DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO;
  
  
 	public ActualizarDetalleSubsidioAsignado (DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO){
 		super();
		this.detalleSubsidioAsignadoDTO=detalleSubsidioAsignadoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(detalleSubsidioAsignadoDTO == null ? null : Entity.json(detalleSubsidioAsignadoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDetalleSubsidioAsignadoDTO (DetalleSubsidioAsignadoDTO detalleSubsidioAsignadoDTO){
 		this.detalleSubsidioAsignadoDTO=detalleSubsidioAsignadoDTO;
 	}
 	
 	public DetalleSubsidioAsignadoDTO getDetalleSubsidioAsignadoDTO (){
 		return detalleSubsidioAsignadoDTO;
 	}
  
}