package com.asopagos.listaschequeo.clients;

import java.util.List;
import com.asopagos.listaschequeo.dto.RequisitoCajaClasificacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/requisitos/cajasCompensacion/clasificaciones
 */
public class CrearRequisitosCajaClasificacion extends ServiceClient { 
    	private List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion;
  
  
 	public CrearRequisitosCajaClasificacion (List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion){
 		super();
		this.requisitosCajaClasificacion=requisitosCajaClasificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(requisitosCajaClasificacion == null ? null : Entity.json(requisitosCajaClasificacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRequisitosCajaClasificacion (List<RequisitoCajaClasificacionDTO> requisitosCajaClasificacion){
 		this.requisitosCajaClasificacion=requisitosCajaClasificacion;
 	}
 	
 	public List<RequisitoCajaClasificacionDTO> getRequisitosCajaClasificacion (){
 		return requisitosCajaClasificacion;
 	}
  
}