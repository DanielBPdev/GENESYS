package com.asopagos.empresas.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/empresas/{idEmpresa}/sucursales
 */
public class ActualizarSucursalEmpresa extends ServiceClient { 
  	private Long idEmpresa;
    	private List<SucursalEmpresa> sucursalesEmpresa;
  
  
 	public ActualizarSucursalEmpresa (Long idEmpresa,List<SucursalEmpresa> sucursalesEmpresa){
 		super();
		this.idEmpresa=idEmpresa;
		this.sucursalesEmpresa=sucursalesEmpresa;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpresa", idEmpresa)
			.request(MediaType.APPLICATION_JSON)
			.put(sucursalesEmpresa == null ? null : Entity.json(sucursalesEmpresa));
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
  
  
  	public void setSucursalesEmpresa (List<SucursalEmpresa> sucursalesEmpresa){
 		this.sucursalesEmpresa=sucursalesEmpresa;
 	}
 	
 	public List<SucursalEmpresa> getSucursalesEmpresa (){
 		return sucursalesEmpresa;
 	}
  
}