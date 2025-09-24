package com.asopagos.subsidiomonetario.clients;

import com.asopagos.dto.subsidiomonetario.pagos.InformacionLiquidacionFallecimientoDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/subsidio/consultarLiquidacionFallecimiento
 */
public class ConsultarLiquidacionFallecimiento extends ServiceClient {
 
  
  	private String tipoIdentificacion;
  	private Long periodoRegular;
  	private String numeroOperacion;
  	private String medioPago;
  	private String identificacion;
  	private Long fechaFin;
  	private Long fechaProgramada;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InformacionLiquidacionFallecimientoDTO> result;
  
 	public ConsultarLiquidacionFallecimiento (String tipoIdentificacion,Long periodoRegular,String numeroOperacion,String medioPago,String identificacion,Long fechaFin,Long fechaProgramada,Long fechaInicio){
 		super();
		this.tipoIdentificacion=tipoIdentificacion;
		this.periodoRegular=periodoRegular;
		this.numeroOperacion=numeroOperacion;
		this.medioPago=medioPago;
		this.identificacion=identificacion;
		this.fechaFin=fechaFin;
		this.fechaProgramada=fechaProgramada;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("periodoRegular", periodoRegular)
						.queryParam("numeroOperacion", numeroOperacion)
						.queryParam("medioPago", medioPago)
						.queryParam("identificacion", identificacion)
						.queryParam("fechaFin", fechaFin)
						.queryParam("fechaProgramada", fechaProgramada)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<InformacionLiquidacionFallecimientoDTO>) response.readEntity(new GenericType<List<InformacionLiquidacionFallecimientoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<InformacionLiquidacionFallecimientoDTO> getResult() {
		return result;
	}

 
  	public void setTipoIdentificacion (String tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public String getTipoIdentificacion (){
 		return tipoIdentificacion;
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
  	public void setMedioPago (String medioPago){
 		this.medioPago=medioPago;
 	}
 	
 	public String getMedioPago (){
 		return medioPago;
 	}
  	public void setIdentificacion (String identificacion){
 		this.identificacion=identificacion;
 	}
 	
 	public String getIdentificacion (){
 		return identificacion;
 	}
  	public void setFechaFin (Long fechaFin){
 		this.fechaFin=fechaFin;
 	}
 	
 	public Long getFechaFin (){
 		return fechaFin;
 	}
  	public void setFechaProgramada (Long fechaProgramada){
 		this.fechaProgramada=fechaProgramada;
 	}
 	
 	public Long getFechaProgramada (){
 		return fechaProgramada;
 	}
  	public void setFechaInicio (Long fechaInicio){
 		this.fechaInicio=fechaInicio;
 	}
 	
 	public Long getFechaInicio (){
 		return fechaInicio;
 	}
  
}