package com.asopagos.personas.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/inactivarCertificadoEscolaridad
 */
public class InactivarCertificadoEscolaridad extends ServiceClient { 
    	private List<Long> idBeneficiariosInactivar;
  
  
 	public InactivarCertificadoEscolaridad (List<Long> idBeneficiariosInactivar){
 		super();
		this.idBeneficiariosInactivar=idBeneficiariosInactivar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idBeneficiariosInactivar == null ? null : Entity.json(idBeneficiariosInactivar));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIdBeneficiariosInactivar (List<Long> idBeneficiariosInactivar){
 		this.idBeneficiariosInactivar=idBeneficiariosInactivar;
 	}
 	
 	public List<Long> getIdBeneficiariosInactivar (){
 		return idBeneficiariosInactivar;
 	}
  
}