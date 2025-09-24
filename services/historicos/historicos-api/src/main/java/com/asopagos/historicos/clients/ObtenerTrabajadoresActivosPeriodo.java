package com.asopagos.historicos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/historicos/obtenerTrabajadoresActivosPeriodo
 */
public class ObtenerTrabajadoresActivosPeriodo extends ServiceClient {
 
  
  	private Long startDate;
  	private Long idEmpleador;
  	private Long endDate;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public ObtenerTrabajadoresActivosPeriodo (Long startDate,Long idEmpleador,Long endDate){
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
		this.result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Long> getResult() {
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