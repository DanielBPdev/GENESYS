package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/actualizarBeneficiario
 */
public class ActualizarBeneficiario extends ServiceClient { 
    	private BeneficiarioModeloDTO beneficiarioModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public ActualizarBeneficiario (BeneficiarioModeloDTO beneficiarioModeloDTO){
 		super();
		this.beneficiarioModeloDTO=beneficiarioModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(beneficiarioModeloDTO == null ? null : Entity.json(beneficiarioModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setBeneficiarioModeloDTO (BeneficiarioModeloDTO beneficiarioModeloDTO){
 		this.beneficiarioModeloDTO=beneficiarioModeloDTO;
 	}
 	
 	public BeneficiarioModeloDTO getBeneficiarioModeloDTO (){
 		return beneficiarioModeloDTO;
 	}
  
}