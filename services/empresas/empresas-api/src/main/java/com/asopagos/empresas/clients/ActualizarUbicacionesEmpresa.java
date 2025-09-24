package com.asopagos.empresas.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.ccf.core.UbicacionEmpresa;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/empresas/{idEmpresa}/ubicaciones
 */
public class ActualizarUbicacionesEmpresa extends ServiceClient { 
  	private Long idEmpresa;
    	private List<UbicacionEmpresa> listUbicaciones;
  
  
 	public ActualizarUbicacionesEmpresa (Long idEmpresa,List<UbicacionEmpresa> listUbicaciones){
 		super();
		this.idEmpresa=idEmpresa;
		this.listUbicaciones=listUbicaciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpresa", idEmpresa)
			.request(MediaType.APPLICATION_JSON)
			.put(listUbicaciones == null ? null : Entity.json(listUbicaciones));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdEmpresa (Long idEmpresa){
 		this.idEmpresa=idEmpresa;
 	}
 	
 	public Long getIdEmpresa (){
 		return idEmpresa;
 	}
  
  
  	public void setListUbicaciones (List<UbicacionEmpresa> listUbicaciones){
 		this.listUbicaciones=listUbicaciones;
 	}
 	
 	public List<UbicacionEmpresa> getListUbicaciones (){
 		return listUbicaciones;
 	}
  
}