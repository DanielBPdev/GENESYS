package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aporteManual/consultarEstadoAfiliadoPorPeriodo
 */
public class ConsultarEstadoCotizantePorPeriodo extends ServiceClient {
 
  
  	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private String numeroIdentificacionAportante;
  	private TipoIdentificacionEnum tipoIdentificacionAportante;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Long periodoAporte;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EstadoAfiliadoEnum result;
  
 	public ConsultarEstadoCotizantePorPeriodo (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,String numeroIdentificacionAportante,TipoIdentificacionEnum tipoIdentificacionAportante,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,Long periodoAporte){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.numeroIdentificacionAportante=numeroIdentificacionAportante;
		this.tipoIdentificacionAportante=tipoIdentificacionAportante;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.periodoAporte=periodoAporte;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoSolicitante", tipoSolicitante)
						.queryParam("numeroIdentificacionAportante", numeroIdentificacionAportante)
						.queryParam("tipoIdentificacionAportante", tipoIdentificacionAportante)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("periodoAporte", periodoAporte)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EstadoAfiliadoEnum) response.readEntity(EstadoAfiliadoEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EstadoAfiliadoEnum getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  	public void setNumeroIdentificacionAportante (String numeroIdentificacionAportante){
 		this.numeroIdentificacionAportante=numeroIdentificacionAportante;
 	}
 	
 	public String getNumeroIdentificacionAportante (){
 		return numeroIdentificacionAportante;
 	}
  	public void setTipoIdentificacionAportante (TipoIdentificacionEnum tipoIdentificacionAportante){
 		this.tipoIdentificacionAportante=tipoIdentificacionAportante;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionAportante (){
 		return tipoIdentificacionAportante;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setPeriodoAporte (Long periodoAporte){
 		this.periodoAporte=periodoAporte;
 	}
 	
 	public Long getPeriodoAporte (){
 		return periodoAporte;
 	}
  
}