package com.asopagos.afiliados.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/retirarBeneficiarioMayorEdadConTI
 */
public class RetirarBeneficiarioMayorEdadConTI extends ServiceClient { 
    	private List<Long> idsBeneficiario;
  
  
 	public RetirarBeneficiarioMayorEdadConTI (List<Long> idsBeneficiario){
 		super();
		this.idsBeneficiario=idsBeneficiario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idsBeneficiario == null ? null : Entity.json(idsBeneficiario));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setIdsBeneficiario (List<Long> idsBeneficiario){
 		this.idsBeneficiario=idsBeneficiario;
 	}
 	
 	public List<Long> getIdsBeneficiario (){
 		return idsBeneficiario;
 	}
  
}