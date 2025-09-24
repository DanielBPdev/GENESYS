package com.asopagos.empleadores.clients;

import com.asopagos.dto.modelo.BeneficioEmpleadorModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/actualizarBeneficioEmpleador
 */
public class ActualizarBeneficioEmpleador extends ServiceClient { 
    	private BeneficioEmpleadorModeloDTO beneficioEmpleador;
  
  
 	public ActualizarBeneficioEmpleador (BeneficioEmpleadorModeloDTO beneficioEmpleador){
 		super();
		this.beneficioEmpleador=beneficioEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(beneficioEmpleador == null ? null : Entity.json(beneficioEmpleador));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setBeneficioEmpleador (BeneficioEmpleadorModeloDTO beneficioEmpleador){
 		this.beneficioEmpleador=beneficioEmpleador;
 	}
 	
 	public BeneficioEmpleadorModeloDTO getBeneficioEmpleador (){
 		return beneficioEmpleador;
 	}
  
}