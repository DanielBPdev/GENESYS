package com.asopagos.afiliaciones.empleadores.web.composite.clients;

import com.asopagos.afiliaciones.empleadores.web.composite.dto.ConsultarConceptoEscalamientoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/consultarConceptoEscalamiento
 */
public class ConsultarConceptoEscalamiento extends ServiceClient { 
    	private ConsultarConceptoEscalamientoDTO entrada;
  
  
 	public ConsultarConceptoEscalamiento (ConsultarConceptoEscalamientoDTO entrada){
 		super();
		this.entrada=entrada;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(entrada == null ? null : Entity.json(entrada));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setEntrada (ConsultarConceptoEscalamientoDTO entrada){
 		this.entrada=entrada;
 	}
 	
 	public ConsultarConceptoEscalamientoDTO getEntrada (){
 		return entrada;
 	}
  
}