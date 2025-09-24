package com.asopagos.empresas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empresas/{idEmpresa}/sucursales
 */
public class CrearSucursalEmpresa extends ServiceClient { 
  	private Long idEmpresa;
    	private List<SucursalEmpresa> listadoSucursales;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public CrearSucursalEmpresa (Long idEmpresa,List<SucursalEmpresa> listadoSucursales){
 		super();
		this.idEmpresa=idEmpresa;
		this.listadoSucursales=listadoSucursales;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpresa", idEmpresa)
			.request(MediaType.APPLICATION_JSON)
			.post(listadoSucursales == null ? null : Entity.json(listadoSucursales));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Long> getResult() {
		return result;
	}

 	public void setIdEmpresa (Long idEmpresa){
 		this.idEmpresa=idEmpresa;
 	}
 	
 	public Long getIdEmpresa (){
 		return idEmpresa;
 	}
  
  
  	public void setListadoSucursales (List<SucursalEmpresa> listadoSucursales){
 		this.listadoSucursales=listadoSucursales;
 	}
 	
 	public List<SucursalEmpresa> getListadoSucursales (){
 		return listadoSucursales;
 	}
  
}