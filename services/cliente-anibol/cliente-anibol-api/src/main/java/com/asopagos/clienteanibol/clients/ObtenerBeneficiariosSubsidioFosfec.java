package com.asopagos.clienteanibol.clients;

import com.asopagos.clienteanibol.dto.ConsultaSubsidioFosfecDTO;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/integracionZenith/obtenerBeneficiariosSubsidioFosfec
 */
public class ObtenerBeneficiariosSubsidioFosfec extends ServiceClient { 
    	private ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Response result;
  
 	public ObtenerBeneficiariosSubsidioFosfec (ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO){
 		super();
		this.consultaSubsidioFosfecDTO=consultaSubsidioFosfecDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(consultaSubsidioFosfecDTO == null ? null : Entity.json(consultaSubsidioFosfecDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Response) response.readEntity(Response.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Response getResult() {
		return result;
	}

 
  
  	public void setConsultaSubsidioFosfecDTO (ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO){
 		this.consultaSubsidioFosfecDTO=consultaSubsidioFosfecDTO;
 	}
 	
 	public ConsultaSubsidioFosfecDTO getConsultaSubsidioFosfecDTO (){
 		return consultaSubsidioFosfecDTO;
 	}
  
}