package com.asopagos.subsidiomonetario.composite.clients;

import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoLiquidacionFallecimientoDTO;
import java.lang.Boolean;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ModoDesembolsoEnum;
import java.lang.String;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.EstadoAporteSubsidioEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetarioComposite/liquidacionFallecimiento/finalizar/{numeroRadicacion}/{idTarea}
 */
public class FinalizarLiquidacionFallecimiento extends ServiceClient { 
  	private String numeroRadicacion;
  	private String idTarea;
   	private EstadoAporteSubsidioEnum estadoAporte;
  	private Boolean consideracionAportes;
  	private ModoDesembolsoEnum tipoDesembolso;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoLiquidacionFallecimientoDTO result;
  
 	public FinalizarLiquidacionFallecimiento (String numeroRadicacion,String idTarea,EstadoAporteSubsidioEnum estadoAporte,Boolean consideracionAportes,ModoDesembolsoEnum tipoDesembolso){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.idTarea=idTarea;
		this.estadoAporte=estadoAporte;
		this.consideracionAportes=consideracionAportes;
		this.tipoDesembolso=tipoDesembolso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.resolveTemplate("idTarea", idTarea)
			.queryParam("estadoAporte", estadoAporte)
			.queryParam("consideracionAportes", consideracionAportes)
			.queryParam("tipoDesembolso", tipoDesembolso)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoLiquidacionFallecimientoDTO) response.readEntity(ResultadoLiquidacionFallecimientoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoLiquidacionFallecimientoDTO getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  	public void setIdTarea (String idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public String getIdTarea (){
 		return idTarea;
 	}
  
  	public void setEstadoAporte (EstadoAporteSubsidioEnum estadoAporte){
 		this.estadoAporte=estadoAporte;
 	}
 	
 	public EstadoAporteSubsidioEnum getEstadoAporte (){
 		return estadoAporte;
 	}
  	public void setConsideracionAportes (Boolean consideracionAportes){
 		this.consideracionAportes=consideracionAportes;
 	}
 	
 	public Boolean getConsideracionAportes (){
 		return consideracionAportes;
 	}
  	public void setTipoDesembolso (ModoDesembolsoEnum tipoDesembolso){
 		this.tipoDesembolso=tipoDesembolso;
 	}
 	
 	public ModoDesembolsoEnum getTipoDesembolso (){
 		return tipoDesembolso;
 	}
  
  
}