package com.asopagos.entidades.pagadoras.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/entidadesPagadoras/{idEntidadPagadora}/solicitudesAsociacionPersonas/guardar/{consecutivoGestion}
 */
public class GuardarIdentificadorArchivo extends ServiceClient { 
  	private Long idEntidadPagadora;
  	private String consecutivoGestion;
    	private String identificadorArchivo;
  
  
 	public GuardarIdentificadorArchivo (Long idEntidadPagadora,String consecutivoGestion,String identificadorArchivo){
 		super();
		this.idEntidadPagadora=idEntidadPagadora;
		this.consecutivoGestion=consecutivoGestion;
		this.identificadorArchivo=identificadorArchivo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEntidadPagadora", idEntidadPagadora)
			.resolveTemplate("consecutivoGestion", consecutivoGestion)
			.request(MediaType.APPLICATION_JSON)
			.post(identificadorArchivo == null ? null : Entity.json(identificadorArchivo));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdEntidadPagadora (Long idEntidadPagadora){
 		this.idEntidadPagadora=idEntidadPagadora;
 	}
 	
 	public Long getIdEntidadPagadora (){
 		return idEntidadPagadora;
 	}
  	public void setConsecutivoGestion (String consecutivoGestion){
 		this.consecutivoGestion=consecutivoGestion;
 	}
 	
 	public String getConsecutivoGestion (){
 		return consecutivoGestion;
 	}
  
  
  	public void setIdentificadorArchivo (String identificadorArchivo){
 		this.identificadorArchivo=identificadorArchivo;
 	}
 	
 	public String getIdentificadorArchivo (){
 		return identificadorArchivo;
 	}
  
}