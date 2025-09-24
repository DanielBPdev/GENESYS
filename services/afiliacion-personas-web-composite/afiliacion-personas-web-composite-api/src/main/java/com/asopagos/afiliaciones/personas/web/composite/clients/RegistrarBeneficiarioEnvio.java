package com.asopagos.afiliaciones.personas.web.composite.clients;

import com.asopagos.dto.BeneficiarioDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/{idAfiliado}/beneficiariosEnvioComunicado
 */
public class RegistrarBeneficiarioEnvio extends ServiceClient { 
  	private Long idAfiliado;
    	private BeneficiarioDTO beneficiarioDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarBeneficiarioEnvio (Long idAfiliado,BeneficiarioDTO beneficiarioDTO){
 		super();
		this.idAfiliado=idAfiliado;
		this.beneficiarioDTO=beneficiarioDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idAfiliado", idAfiliado)
			.request(MediaType.APPLICATION_JSON)
			.post(beneficiarioDTO == null ? null : Entity.json(beneficiarioDTO));
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

 	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
  
  	public void setBeneficiarioDTO (BeneficiarioDTO beneficiarioDTO){
 		this.beneficiarioDTO=beneficiarioDTO;
 	}
 	
 	public BeneficiarioDTO getBeneficiarioDTO (){
 		return beneficiarioDTO;
 	}
  
}