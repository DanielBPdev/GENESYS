package com.asopagos.empresas.clients;

import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empresas/{idEmpresa}/sucursalPila
 */
public class CrearSucursalEmpresaPila extends ServiceClient { 
  	private Long idEmpresa;
    	private SucursalEmpresaModeloDTO sucursalDTO;
  
  
 	public CrearSucursalEmpresaPila (Long idEmpresa,SucursalEmpresaModeloDTO sucursalDTO){
 		super();
		this.idEmpresa=idEmpresa;
		this.sucursalDTO=sucursalDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpresa", idEmpresa)
			.request(MediaType.APPLICATION_JSON)
			.post(sucursalDTO == null ? null : Entity.json(sucursalDTO));
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
  
  
  	public void setSucursalDTO (SucursalEmpresaModeloDTO sucursalDTO){
 		this.sucursalDTO=sucursalDTO;
 	}
 	
 	public SucursalEmpresaModeloDTO getSucursalDTO (){
 		return sucursalDTO;
 	}
  
}