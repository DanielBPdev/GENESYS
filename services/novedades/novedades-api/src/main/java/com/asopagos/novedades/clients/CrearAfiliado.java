package com.asopagos.novedades.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/crearAfiliado
 */
public class CrearAfiliado extends ServiceClient { 
   	private String ni;
  	private String nombreAfiliado;
  	private String ti;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearAfiliado (String ni,String nombreAfiliado,String ti){
 		super();
		this.ni=ni;
		this.nombreAfiliado=nombreAfiliado;
		this.ti=ti;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("ni", ni)
			.queryParam("nombreAfiliado", nombreAfiliado)
			.queryParam("ti", ti)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  	public void setNi (String ni){
 		this.ni=ni;
 	}
 	
 	public String getNi (){
 		return ni;
 	}
  	public void setNombreAfiliado (String nombreAfiliado){
 		this.nombreAfiliado=nombreAfiliado;
 	}
 	
 	public String getNombreAfiliado (){
 		return nombreAfiliado;
 	}
  	public void setTi (String ti){
 		this.ti=ti;
 	}
 	
 	public String getTi (){
 		return ti;
 	}
  
  
}