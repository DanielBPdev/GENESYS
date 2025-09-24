package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.subsidiomonetario.dto.ResultadoHistoricoLiquidacionMasivaDTO;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoLiquidacionEspecificaEnum;
import java.lang.String;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarHistoricoLiquidacionEspecifica
 */
public class ConsultarHistoricoLiquidacionEspecifica extends ServiceClient {
 
  
  	private TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica;
  	private TipoProcesoLiquidacionEnum tipoLiquidacion;
  	private Long periodoRegular;
  	private String numeroOperacion;
  	private Long fechaFin;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ResultadoHistoricoLiquidacionMasivaDTO> result;
  
 	public ConsultarHistoricoLiquidacionEspecifica (TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica,TipoProcesoLiquidacionEnum tipoLiquidacion,Long periodoRegular,String numeroOperacion,Long fechaFin,Long fechaInicio){
 		super();
		this.tipoLiquidacionEspecifica=tipoLiquidacionEspecifica;
		this.tipoLiquidacion=tipoLiquidacion;
		this.periodoRegular=periodoRegular;
		this.numeroOperacion=numeroOperacion;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoLiquidacionEspecifica", tipoLiquidacionEspecifica)
						.queryParam("tipoLiquidacion", tipoLiquidacion)
						.queryParam("periodoRegular", periodoRegular)
						.queryParam("numeroOperacion", numeroOperacion)
						.queryParam("fechaFin", fechaFin)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ResultadoHistoricoLiquidacionMasivaDTO>) response.readEntity(new GenericType<List<ResultadoHistoricoLiquidacionMasivaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ResultadoHistoricoLiquidacionMasivaDTO> getResult() {
		return result;
	}

 
  	public void setTipoLiquidacionEspecifica (TipoLiquidacionEspecificaEnum tipoLiquidacionEspecifica){
 		this.tipoLiquidacionEspecifica=tipoLiquidacionEspecifica;
 	}
 	
 	public TipoLiquidacionEspecificaEnum getTipoLiquidacionEspecifica (){
 		return tipoLiquidacionEspecifica;
 	}
  	public void setTipoLiquidacion (TipoProcesoLiquidacionEnum tipoLiquidacion){
 		this.tipoLiquidacion=tipoLiquidacion;
 	}
 	
 	public TipoProcesoLiquidacionEnum getTipoLiquidacion (){
 		return tipoLiquidacion;
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