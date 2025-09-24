package com.asopagos.usuarios.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/grupos/{idGrupo}/inactivar
 */
public class InactivarMiembrosGrupo extends ServiceClient { 
  	private String idGrupo;
    
  
 	public InactivarMiembrosGrupo (String idGrupo){
 		super();
		this.idGrupo=idGrupo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idGrupo", idGrupo)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdGrupo (String idGrupo){
 		this.idGrupo=idGrupo;
 	}
 	
 	public String getIdGrupo (){
 		return idGrupo;
 	}
  
  
  
}