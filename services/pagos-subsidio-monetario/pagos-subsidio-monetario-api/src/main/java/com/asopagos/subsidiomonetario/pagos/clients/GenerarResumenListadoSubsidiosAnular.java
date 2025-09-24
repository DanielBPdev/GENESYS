package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.ResumenListadoSubsidiosAnularDTO;
import java.lang.Boolean;
import java.lang.String;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/generarResumenListado/subsidiosAnular
 */
public class GenerarResumenListadoSubsidiosAnular extends ServiceClient {
 
  
  	private Integer offset;
  	private String tipo;
  	private Integer limit;
  	private String orderBy;
  	private Integer filter;
  	private Boolean firstRequest;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResumenListadoSubsidiosAnularDTO result;
  
 	public GenerarResumenListadoSubsidiosAnular (Integer offset,String tipo,Integer limit,String orderBy,Integer filter,Boolean firstRequest){
 		super();
		this.offset=offset;
		this.tipo=tipo;
		this.limit=limit;
		this.orderBy=orderBy;
		this.filter=filter;
		this.firstRequest=firstRequest;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("offset", offset)
						.queryParam("tipo", tipo)
						.queryParam("limit", limit)
						.queryParam("orderBy", orderBy)
						.queryParam("filter", filter)
						.queryParam("firstRequest", firstRequest)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ResumenListadoSubsidiosAnularDTO) response.readEntity(ResumenListadoSubsidiosAnularDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ResumenListadoSubsidiosAnularDTO getResult() {
		return result;
	}

 
  	public void setOffset (Integer offset){
 		this.offset=offset;
 	}
 	
 	public Integer getOffset (){
 		return offset;
 	}
  	public void setTipo (String tipo){
 		this.tipo=tipo;
 	}
 	
 	public String getTipo (){
 		return tipo;
 	}
  	public void setLimit (Integer limit){
 		this.limit=limit;
 	}
 	
 	public Integer getLimit (){
 		return limit;
 	}
  	public void setOrderBy (String orderBy){
 		this.orderBy=orderBy;
 	}
 	
 	public String getOrderBy (){
 		return orderBy;
 	}
  	public void setFilter (Integer filter){
 		this.filter=filter;
 	}
 	
 	public Integer getFilter (){
 		return filter;
 	}
  	public void setFirstRequest (Boolean firstRequest){
 		this.firstRequest=firstRequest;
 	}
 	
 	public Boolean getFirstRequest (){
 		return firstRequest;
 	}
  
}