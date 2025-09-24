package com.asopagos.afiliaciones.clients;

import com.asopagos.dto.ProductoNoConformeDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliaciones/{idSolicitudAfiliacion}/productoNoConforme/{idProductoNoConforme}
 */
public class ActualizarProductoNoConforme extends ServiceClient { 
  	private Long idSolicitudAfiliacion;
  	private Long idProductoNoConforme;
    	private ProductoNoConformeDTO productoNoConforme;
  
  
 	public ActualizarProductoNoConforme (Long idSolicitudAfiliacion,Long idProductoNoConforme,ProductoNoConformeDTO productoNoConforme){
 		super();
		this.idSolicitudAfiliacion=idSolicitudAfiliacion;
		this.idProductoNoConforme=idProductoNoConforme;
		this.productoNoConforme=productoNoConforme;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudAfiliacion", idSolicitudAfiliacion)
			.resolveTemplate("idProductoNoConforme", idProductoNoConforme)
			.request(MediaType.APPLICATION_JSON)
			.put(productoNoConforme == null ? null : Entity.json(productoNoConforme));
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
  	public void setIdProductoNoConforme (Long idProductoNoConforme){
 		this.idProductoNoConforme=idProductoNoConforme;
 	}
 	
 	public Long getIdProductoNoConforme (){
 		return idProductoNoConforme;
 	}
  
  
  	public void setProductoNoConforme (ProductoNoConformeDTO productoNoConforme){
 		this.productoNoConforme=productoNoConforme;
 	}
 	
 	public ProductoNoConformeDTO getProductoNoConforme (){
 		return productoNoConforme;
 	}
  
}