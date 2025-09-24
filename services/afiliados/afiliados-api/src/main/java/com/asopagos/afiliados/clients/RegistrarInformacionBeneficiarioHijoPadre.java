package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.BeneficiarioHijoPadreDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/{idAfiliado}/beneficiarios/hijos
 */
public class RegistrarInformacionBeneficiarioHijoPadre extends ServiceClient { 
  	private Long idAfiliado;
    	private BeneficiarioHijoPadreDTO inDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarInformacionBeneficiarioHijoPadre (Long idAfiliado,BeneficiarioHijoPadreDTO inDTO){
 		super();
		this.idAfiliado=idAfiliado;
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idAfiliado", idAfiliado)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
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
  
  
  	public void setInDTO (BeneficiarioHijoPadreDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public BeneficiarioHijoPadreDTO getInDTO (){
 		return inDTO;
 	}
  
}