package com.asopagos.historicos.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/historicos/obtenerEstadoEmpleadorPeriodo
 */
public class ObtenerEstadoEmpleadorPeriodo extends ServiceClient {
 
  
  	private Long startDate;
  	private Long idEmpleador;
  	private Long endDate;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EstadoEmpleadorEnum result;
  
 	public ObtenerEstadoEmpleadorPeriodo (Long startDate,Long idEmpleador,Long endDate){
 		super();
		this.startDate=startDate;
		this.idEmpleador=idEmpleador;
		this.endDate=endDate;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("startDate", startDate)
						.queryParam("idEmpleador", idEmpleador)
						.queryParam("endDate", endDate)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EstadoEmpleadorEnum) response.readEntity(EstadoEmpleadorEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EstadoEmpleadorEnum getResult() {
		return result;
	}

 
  	public void setStartDate (Long startDate){
 		this.startDate=startDate;
 	}
 	
 	public Long getStartDate (){
 		return startDate;
 	}
  	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  	public void setEndDate (Long endDate){
 		this.endDate=endDate;
 	}
 	
 	public Long getEndDate (){
 		return endDate;
 	}
  
}