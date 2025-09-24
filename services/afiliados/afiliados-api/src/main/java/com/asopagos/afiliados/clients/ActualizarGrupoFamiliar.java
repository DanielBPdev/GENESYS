package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.GrupoFamiliarDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliados/{idAfiliado}/gruposFamiliares
 */
public class ActualizarGrupoFamiliar extends ServiceClient { 
  	private Long idAfiliado;
    	private GrupoFamiliarDTO grupoFamiliarDTO;
  
  
 	public ActualizarGrupoFamiliar (Long idAfiliado,GrupoFamiliarDTO grupoFamiliarDTO){
 		super();
		this.idAfiliado=idAfiliado;
		this.grupoFamiliarDTO=grupoFamiliarDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idAfiliado", idAfiliado)
			.request(MediaType.APPLICATION_JSON)
			.put(grupoFamiliarDTO == null ? null : Entity.json(grupoFamiliarDTO));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
  
  	public void setGrupoFamiliarDTO (GrupoFamiliarDTO grupoFamiliarDTO){
 		this.grupoFamiliarDTO=grupoFamiliarDTO;
 	}
 	
 	public GrupoFamiliarDTO getGrupoFamiliarDTO (){
 		return grupoFamiliarDTO;
 	}
  
}