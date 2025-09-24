package com.asopagos.cartera.composite.clients;

import com.asopagos.enumeraciones.cartera.TipoActividadBitacoraEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import com.asopagos.enumeraciones.cartera.ResultadoBitacoraCarteraEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/carteraComposite/guardarBitacoraLiquidacion
 */
public class GuardarBitacoraLiquidacion extends ServiceClient {
 
  
  	private Long numeroOperacion;
  	private TipoSolicitanteMovimientoAporteEnum tipoAportante;
  	private ResultadoBitacoraCarteraEnum resultado;
  	private String idECM;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private TipoActividadBitacoraEnum tipoActividad;
  
  
 	public GuardarBitacoraLiquidacion (Long numeroOperacion,TipoSolicitanteMovimientoAporteEnum tipoAportante,ResultadoBitacoraCarteraEnum resultado,String idECM,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,TipoActividadBitacoraEnum tipoActividad){
 		super();
		this.numeroOperacion=numeroOperacion;
		this.tipoAportante=tipoAportante;
		this.resultado=resultado;
		this.idECM=idECM;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.tipoActividad=tipoActividad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroOperacion", numeroOperacion)
						.queryParam("tipoAportante", tipoAportante)
						.queryParam("resultado", resultado)
						.queryParam("idECM", idECM)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("tipoActividad", tipoActividad)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNumeroOperacion (Long numeroOperacion){
 		this.numeroOperacion=numeroOperacion;
 	}
 	
 	public Long getNumeroOperacion (){
 		return numeroOperacion;
 	}
  	public void setTipoAportante (TipoSolicitanteMovimientoAporteEnum tipoAportante){
 		this.tipoAportante=tipoAportante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoAportante (){
 		return tipoAportante;
 	}
  	public void setResultado (ResultadoBitacoraCarteraEnum resultado){
 		this.resultado=resultado;
 	}
 	
 	public ResultadoBitacoraCarteraEnum getResultado (){
 		return resultado;
 	}
  	public void setIdECM (String idECM){
 		this.idECM=idECM;
 	}
 	
 	public String getIdECM (){
 		return idECM;
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
  	public void setTipoActividad (TipoActividadBitacoraEnum tipoActividad){
 		this.tipoActividad=tipoActividad;
 	}
 	
 	public TipoActividadBitacoraEnum getTipoActividad (){
 		return tipoActividad;
 	}
  
}