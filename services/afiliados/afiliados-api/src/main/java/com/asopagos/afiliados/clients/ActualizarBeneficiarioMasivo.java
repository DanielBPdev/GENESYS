package com.asopagos.afiliados.clients;

import com.asopagos.dto.BeneficiarioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/actualizarBeneficiarioMasivo
 */
public class ActualizarBeneficiarioMasivo extends ServiceClient { 
    	private BeneficiarioDTO beneficiario;
  
  
 	public ActualizarBeneficiarioMasivo (BeneficiarioDTO beneficiario){
 		super();
		this.beneficiario=beneficiario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(beneficiario == null ? null : Entity.json(beneficiario));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setBeneficiario (BeneficiarioDTO beneficiario){
 		this.beneficiario=beneficiario;
 	}
 	
 	public BeneficiarioDTO getBeneficiario (){
 		return beneficiario;
 	}
  
}