package com.asopagos.cartera.clients;

import java.math.BigDecimal;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/obtenerDeudaPresuntaCartera
 */
public class ObtenerDeudaPresuntaCartera extends ServiceClient {
 
  
  	private TipoSolicitanteMovimientoAporteEnum tipoAportante;
  	private String numeroIdentificacion;
  	private String periodoEvaluacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private BigDecimal result;
  
 	public ObtenerDeudaPresuntaCartera (TipoSolicitanteMovimientoAporteEnum tipoAportante,String numeroIdentificacion,String periodoEvaluacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.tipoAportante=tipoAportante;
		this.numeroIdentificacion=numeroIdentificacion;
		this.periodoEvaluacion=periodoEvaluacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoAportante", tipoAportante)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("periodoEvaluacion", periodoEvaluacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (BigDecimal) response.readEntity(BigDecimal.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public BigDecimal getResult() {
		return result;
	}

 
  	public void setTipoAportante (TipoSolicitanteMovimientoAporteEnum tipoAportante){
 		this.tipoAportante=tipoAportante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoAportante (){
 		return tipoAportante;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setPeriodoEvaluacion (String periodoEvaluacion){
 		this.periodoEvaluacion=periodoEvaluacion;
 	}
 	
 	public String getPeriodoEvaluacion (){
 		return periodoEvaluacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
}