package com.asopagos.subsidiomonetario.pagos.clients;

import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleSubsidioAsignadoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/registrarDetalleSubsidioAsignado
 */
public class RegistrarDetalleSubsidioAsignado extends ServiceClient { 
    	private List<DetalleSubsidioAsignadoDTO> detallesSubsidioAsignadoDTO;
  
  
 	public RegistrarDetalleSubsidioAsignado (List<DetalleSubsidioAsignadoDTO> detallesSubsidioAsignadoDTO){
 		super();
		this.detallesSubsidioAsignadoDTO=detallesSubsidioAsignadoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(detallesSubsidioAsignadoDTO == null ? null : Entity.json(detallesSubsidioAsignadoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDetallesSubsidioAsignadoDTO (List<DetalleSubsidioAsignadoDTO> detallesSubsidioAsignadoDTO){
 		this.detallesSubsidioAsignadoDTO=detallesSubsidioAsignadoDTO;
 	}
 	
 	public List<DetalleSubsidioAsignadoDTO> getDetallesSubsidioAsignadoDTO (){
 		return detallesSubsidioAsignadoDTO;
 	}
  
}