package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.subsidiomonetario.liquidacion.ResultadoHistoricoLiquidacionFallecimientoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarHistoricoLiquidacionFallecimiento
 */
public class ConsultarHistoricoLiquidacionFallecimiento extends ServiceClient {
 
  
  	private Long periodoRegular;
  	private String numeroOperacion;
  	private TipoIdentificacionEnum tipoIdentificaccion;
  	private String numeroIdentificacion;
  	private Long fechaFin;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ResultadoHistoricoLiquidacionFallecimientoDTO> result;
  
 	public ConsultarHistoricoLiquidacionFallecimiento (Long periodoRegular,String numeroOperacion,TipoIdentificacionEnum tipoIdentificaccion,String numeroIdentificacion,Long fechaFin,Long fechaInicio){
 		super();
		this.periodoRegular=periodoRegular;
		this.numeroOperacion=numeroOperacion;
		this.tipoIdentificaccion=tipoIdentificaccion;
		this.numeroIdentificacion=numeroIdentificacion;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("periodoRegular", periodoRegular)
						.queryParam("numeroOperacion", numeroOperacion)
						.queryParam("tipoIdentificaccion", tipoIdentificaccion)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("fechaFin", fechaFin)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ResultadoHistoricoLiquidacionFallecimientoDTO>) response.readEntity(new GenericType<List<ResultadoHistoricoLiquidacionFallecimientoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ResultadoHistoricoLiquidacionFallecimientoDTO> getResult() {
		return result;
	}

 
  	public void setPeriodoRegular (Long periodoRegular){
 		this.periodoRegular=periodoRegular;
 	}
 	
 	public Long getPeriodoRegular (){
 		return periodoRegular;
 	}
  	public void setNumeroOperacion (String numeroOperacion){
 		this.numeroOperacion=numeroOperacion;
 	}
 	
 	public String getNumeroOperacion (){
 		return numeroOperacion;
 	}
  	public void setTipoIdentificaccion (TipoIdentificacionEnum tipoIdentificaccion){
 		this.tipoIdentificaccion=tipoIdentificaccion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificaccion (){
 		return tipoIdentificaccion;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setFechaFin (Long fechaFin){
 		this.fechaFin=fechaFin;
 	}
 	
 	public Long getFechaFin (){
 		return fechaFin;
 	}
  	public void setFechaInicio (Long fechaInicio){
 		this.fechaInicio=fechaInicio;
 	}
 	
 	public Long getFechaInicio (){
 		return fechaInicio;
 	}
  
}