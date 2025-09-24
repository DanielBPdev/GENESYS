package com.asopagos.afiliados.clients;

import com.asopagos.dto.IdentificacionUbicacionPersonaDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/{idAfiliado}/beneficiarios/conyugue
 */
public class RegistrarInformacionBeneficiarioConyugue extends ServiceClient { 
  	private Long idAfiliado;
    	private IdentificacionUbicacionPersonaDTO inDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarInformacionBeneficiarioConyugue (Long idAfiliado,IdentificacionUbicacionPersonaDTO inDTO){
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
  
  
  	public void setInDTO (IdentificacionUbicacionPersonaDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public IdentificacionUbicacionPersonaDTO getInDTO (){
 		return inDTO;
 	}
  
}