package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.RolDTO;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/grupos/actualizarGrupoRoles/{idGrupo}
 */
public class ActualizarGrupoRoles extends ServiceClient { 
  	private String idGrupo;
    	private List<RolDTO> lstRoles;
  
  
 	public ActualizarGrupoRoles (String idGrupo,List<RolDTO> lstRoles){
 		super();
		this.idGrupo=idGrupo;
		this.lstRoles=lstRoles;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idGrupo", idGrupo)
			.request(MediaType.APPLICATION_JSON)
			.put(lstRoles == null ? null : Entity.json(lstRoles));
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
  
  
  	public void setLstRoles (List<RolDTO> lstRoles){
 		this.lstRoles=lstRoles;
 	}
 	
 	public List<RolDTO> getLstRoles (){
 		return lstRoles;
 	}
  
}