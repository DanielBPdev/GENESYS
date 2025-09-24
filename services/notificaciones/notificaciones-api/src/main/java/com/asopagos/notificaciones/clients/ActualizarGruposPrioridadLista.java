package com.asopagos.notificaciones.clients;

import com.asopagos.notificaciones.dto.GrupoRolPrioridadDTO;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/enviarCorreo/actualizarGruposPrioridadLista/{proceso}
 */
public class ActualizarGruposPrioridadLista extends ServiceClient { 
  	private String proceso;
    	private List<GrupoRolPrioridadDTO> gruposPrioridadDTO;
  
  
 	public ActualizarGruposPrioridadLista (String proceso,List<GrupoRolPrioridadDTO> gruposPrioridadDTO){
 		super();
		this.proceso=proceso;
		this.gruposPrioridadDTO=gruposPrioridadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("proceso", proceso)
			.request(MediaType.APPLICATION_JSON)
			.post(gruposPrioridadDTO == null ? null : Entity.json(gruposPrioridadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setProceso (String proceso){
 		this.proceso=proceso;
 	}
 	
 	public String getProceso (){
 		return proceso;
 	}
  
  
  	public void setGruposPrioridadDTO (List<GrupoRolPrioridadDTO> gruposPrioridadDTO){
 		this.gruposPrioridadDTO=gruposPrioridadDTO;
 	}
 	
 	public List<GrupoRolPrioridadDTO> getGruposPrioridadDTO (){
 		return gruposPrioridadDTO;
 	}
  
}