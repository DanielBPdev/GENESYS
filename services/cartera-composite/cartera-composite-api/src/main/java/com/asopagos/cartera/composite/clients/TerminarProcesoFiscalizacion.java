package com.asopagos.cartera.composite.clients;

import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/{idTarea}/terminarProcesoFiscalizacion
 */
public class TerminarProcesoFiscalizacion extends ServiceClient { 
  	private Long idTarea;
   	private String numeroRadicacion;
  	private EstadoFiscalizacionEnum estadoFiscalizacion;
   
  
 	public TerminarProcesoFiscalizacion (Long idTarea,String numeroRadicacion,EstadoFiscalizacionEnum estadoFiscalizacion){
 		super();
		this.idTarea=idTarea;
		this.numeroRadicacion=numeroRadicacion;
		this.estadoFiscalizacion=estadoFiscalizacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTarea", idTarea)
			.queryParam("numeroRadicacion", numeroRadicacion)
			.queryParam("estadoFiscalizacion", estadoFiscalizacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdTarea (Long idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public Long getIdTarea (){
 		return idTarea;
 	}
  
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  	public void setEstadoFiscalizacion (EstadoFiscalizacionEnum estadoFiscalizacion){
 		this.estadoFiscalizacion=estadoFiscalizacion;
 	}
 	
 	public EstadoFiscalizacionEnum getEstadoFiscalizacion (){
 		return estadoFiscalizacion;
 	}
  
  
}