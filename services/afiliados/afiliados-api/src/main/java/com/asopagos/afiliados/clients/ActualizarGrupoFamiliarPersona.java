package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.GrupoFamiliarModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/actualizarGrupoFamiliarPersona
 */
public class ActualizarGrupoFamiliarPersona extends ServiceClient { 
    	private GrupoFamiliarModeloDTO grupoFamiliarModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public ActualizarGrupoFamiliarPersona (GrupoFamiliarModeloDTO grupoFamiliarModeloDTO){
 		super();
		this.grupoFamiliarModeloDTO=grupoFamiliarModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(grupoFamiliarModeloDTO == null ? null : Entity.json(grupoFamiliarModeloDTO));
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

 
  
  	public void setGrupoFamiliarModeloDTO (GrupoFamiliarModeloDTO grupoFamiliarModeloDTO){
 		this.grupoFamiliarModeloDTO=grupoFamiliarModeloDTO;
 	}
 	
 	public GrupoFamiliarModeloDTO getGrupoFamiliarModeloDTO (){
 		return grupoFamiliarModeloDTO;
 	}
  
}