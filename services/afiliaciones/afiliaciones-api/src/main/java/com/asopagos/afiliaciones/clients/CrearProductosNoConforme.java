package com.asopagos.afiliaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.ProductoNoConformeDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/{idSolicitudAfiliacion}/productoNoConforme
 */
public class CrearProductosNoConforme extends ServiceClient { 
  	private Long idSolicitudAfiliacion;
    	private List<ProductoNoConformeDTO> productosNoConforme;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public CrearProductosNoConforme (Long idSolicitudAfiliacion,List<ProductoNoConformeDTO> productosNoConforme){
 		super();
		this.idSolicitudAfiliacion=idSolicitudAfiliacion;
		this.productosNoConforme=productosNoConforme;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudAfiliacion", idSolicitudAfiliacion)
			.request(MediaType.APPLICATION_JSON)
			.post(productosNoConforme == null ? null : Entity.json(productosNoConforme));
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

 	public void setIdSolicitudAfiliacion (Long idSolicitudAfiliacion){
 		this.idSolicitudAfiliacion=idSolicitudAfiliacion;
 	}
 	
 	public Long getIdSolicitudAfiliacion (){
 		return idSolicitudAfiliacion;
 	}
  
  
  	public void setProductosNoConforme (List<ProductoNoConformeDTO> productosNoConforme){
 		this.productosNoConforme=productosNoConforme;
 	}
 	
 	public List<ProductoNoConformeDTO> getProductosNoConforme (){
 		return productosNoConforme;
 	}
  
}