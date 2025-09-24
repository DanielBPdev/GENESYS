package com.asopagos.historicos.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.EstadoEmpleadorEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/historicos/obtenerEstadoAportantePeriodo
 */
public class ObtenerEstadoAportantePeriodo extends ServiceClient {
 
  
  	private Long startDate;
  	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private Long idAportante;
  	private Long endDate;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EstadoEmpleadorEnum result;
  
 	public ObtenerEstadoAportantePeriodo (Long startDate,TipoSolicitanteMovimientoAporteEnum tipoSolicitante,Long idAportante,Long endDate){
 		super();
		this.startDate=startDate;
		this.tipoSolicitante=tipoSolicitante;
		this.idAportante=idAportante;
		this.endDate=endDate;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("startDate", startDate)
						.queryParam("tipoSolicitante", tipoSolicitante)
						.queryParam("idAportante", idAportante)
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
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  	public void setIdAportante (Long idAportante){
 		this.idAportante=idAportante;
 	}
 	
 	public Long getIdAportante (){
 		return idAportante;
 	}
  	public void setEndDate (Long endDate){
 		this.endDate=endDate;
 	}
 	
 	public Long getEndDate (){
 		return endDate;
 	}
  
}