package com.asopagos.empresas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.ccf.core.UbicacionEmpresa;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empresas/{idEmpresa}/ubicaciones
 */
public class CrearUbicacionesEmpresa extends ServiceClient { 
  	private Long idEmpresa;
    	private List<UbicacionEmpresa> ubicaciones;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public CrearUbicacionesEmpresa (Long idEmpresa,List<UbicacionEmpresa> ubicaciones){
 		super();
		this.idEmpresa=idEmpresa;
		this.ubicaciones=ubicaciones;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpresa", idEmpresa)
			.request(MediaType.APPLICATION_JSON)
			.post(ubicaciones == null ? null : Entity.json(ubicaciones));
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
  
  
  	public void setUbicaciones (List<UbicacionEmpresa> ubicaciones){
 		this.ubicaciones=ubicaciones;
 	}
 	
 	public List<UbicacionEmpresa> getUbicaciones (){
 		return ubicaciones;
 	}
  
}