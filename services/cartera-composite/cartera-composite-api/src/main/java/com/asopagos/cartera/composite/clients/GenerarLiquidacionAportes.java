package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.LiquidacionAporteCarteraDTO;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/carteraComposite/generarLiquidacionAportes
 */
public class GenerarLiquidacionAportes extends ServiceClient {
 
  
  	private TipoLineaCobroEnum lineaCobro;
  	private TipoSolicitanteMovimientoAporteEnum tipoAportante;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private LiquidacionAporteCarteraDTO result;
  
 	public GenerarLiquidacionAportes (TipoLineaCobroEnum lineaCobro,TipoSolicitanteMovimientoAporteEnum tipoAportante,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.lineaCobro=lineaCobro;
		this.tipoAportante=tipoAportante;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("lineaCobro", lineaCobro)
						.queryParam("tipoAportante", tipoAportante)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (LiquidacionAporteCarteraDTO) response.readEntity(LiquidacionAporteCarteraDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public LiquidacionAporteCarteraDTO getResult() {
		return result;
	}

 
  	public void setLineaCobro (TipoLineaCobroEnum lineaCobro){
 		this.lineaCobro=lineaCobro;
 	}
 	
 	public TipoLineaCobroEnum getLineaCobro (){
 		return lineaCobro;
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
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
}