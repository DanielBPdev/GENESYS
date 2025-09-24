package com.asopagos.afiliaciones.clients;

import java.util.List;
import com.asopagos.dto.ProductoNoConformeDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliaciones/{idSolicitudAfiliacion}/productoNoConforme
 */
public class ActualizarProductoNoConforme1 extends ServiceClient { 
  	private Long idSolicitudAfiliacion;
    	private List<ProductoNoConformeDTO> listProductoNoConforme;
  
  
 	public ActualizarProductoNoConforme1 (Long idSolicitudAfiliacion,List<ProductoNoConformeDTO> listProductoNoConforme){
 		super();
		this.idSolicitudAfiliacion=idSolicitudAfiliacion;
		this.listProductoNoConforme=listProductoNoConforme;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudAfiliacion", idSolicitudAfiliacion)
			.request(MediaType.APPLICATION_JSON)
			.put(listProductoNoConforme == null ? null : Entity.json(listProductoNoConforme));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdSolicitudAfiliacion (Long idSolicitudAfiliacion){
 		this.idSolicitudAfiliacion=idSolicitudAfiliacion;
 	}
 	
 	public Long getIdSolicitudAfiliacion (){
 		return idSolicitudAfiliacion;
 	}
  
  
  	public void setListProductoNoConforme (List<ProductoNoConformeDTO> listProductoNoConforme){
 		this.listProductoNoConforme=listProductoNoConforme;
 	}
 	
 	public List<ProductoNoConformeDTO> getListProductoNoConforme (){
 		return listProductoNoConforme;
 	}
  
}