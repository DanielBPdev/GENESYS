package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.ListadoSubsidiosAnularDTO;
import java.lang.Boolean;
import java.lang.String;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/generarListado/subsidiosAnular
 */
public class GenerarlistadoSubsidiosAnular extends ServiceClient {
 
  
  	private Integer offset;
  	private String tipo;
  	private Integer limit;
  	private String orderBy;
  	private Boolean firstRequest;
	private String numeroIdentificacionAdminSub;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ListadoSubsidiosAnularDTO result;
  
 	public GenerarlistadoSubsidiosAnular (Integer offset,String tipo,Integer limit,String orderBy,Boolean firstRequest,String numeroIdentificacionAdminSub){
 		super();
		this.offset=offset;
		this.tipo=tipo;
		this.limit=limit;
		this.orderBy=orderBy;
		this.firstRequest=firstRequest;
		this.numeroIdentificacionAdminSub=numeroIdentificacionAdminSub;

 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("offset", offset)
						.queryParam("tipo", tipo)
						.queryParam("limit", limit)
						.queryParam("orderBy", orderBy)
						.queryParam("firstRequest", firstRequest)
						.queryParam("numeroIdentificacionAdminSub", numeroIdentificacionAdminSub)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ListadoSubsidiosAnularDTO) response.readEntity(ListadoSubsidiosAnularDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ListadoSubsidiosAnularDTO getResult() {
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
  	public void setFirstRequest (Boolean firstRequest){
 		this.firstRequest=firstRequest;
 	}
 	
 	public Boolean getFirstRequest (){
 		return firstRequest;
 	}

	public Boolean isFirstRequest() {
		return this.firstRequest;
	}

	public String getNumeroIdentificacionAdminSub() {
		return this.numeroIdentificacionAdminSub;
	}

	public void setNumeroIdentificacionAdminSub(String numeroIdentificacionAdminSub) {
		this.numeroIdentificacionAdminSub = numeroIdentificacionAdminSub;
	}
  
}