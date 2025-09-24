package com.asopagos.afiliados.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliados/actualizarBeneficiariomasivamente/{idGrupoFamiliar}/{idAfiliado}
 */
public class ActualizarBeneficiariomasivamente extends ServiceClient { 
  	private Long idGrupoFamiliar;
  	private Long idAfiliado;
    
  
 	public ActualizarBeneficiariomasivamente (Long idGrupoFamiliar,Long idAfiliado){
 		super();
		this.idGrupoFamiliar=idGrupoFamiliar;
		this.idAfiliado=idAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idGrupoFamiliar", idGrupoFamiliar)
			.resolveTemplate("idAfiliado", idAfiliado)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdGrupoFamiliar (Long idGrupoFamiliar){
 		this.idGrupoFamiliar=idGrupoFamiliar;
 	}
 	
 	public Long getIdGrupoFamiliar (){
 		return idGrupoFamiliar;
 	}
  	public void setIdAfiliado (Long idAfiliado){
 		this.idAfiliado=idAfiliado;
 	}
 	
 	public Long getIdAfiliado (){
 		return idAfiliado;
 	}
  
  
  
}