package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.BeneficiarioDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/{idAfiliado}/registrarPersonasBeneficiariosAbreviada
 */
public class RegistrarPersonasBeneficiariosAbreviada extends ServiceClient { 
  	private Long idAfiliado;
    	private List<BeneficiarioDTO> beneficiariosDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public RegistrarPersonasBeneficiariosAbreviada (Long idAfiliado,List<BeneficiarioDTO> beneficiariosDTO){
 		super();
		this.idAfiliado=idAfiliado;
		this.beneficiariosDTO=beneficiariosDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idAfiliado", idAfiliado)
			.request(MediaType.APPLICATION_JSON)
			.post(beneficiariosDTO == null ? null : Entity.json(beneficiariosDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Long> getResult() {
		return result;
	}

 	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
  
  	public void setBeneficiariosDTO (List<BeneficiarioDTO> beneficiariosDTO){
 		this.beneficiariosDTO=beneficiariosDTO;
 	}
 	
 	public List<BeneficiarioDTO> getBeneficiariosDTO (){
 		return beneficiariosDTO;
 	}
  
}