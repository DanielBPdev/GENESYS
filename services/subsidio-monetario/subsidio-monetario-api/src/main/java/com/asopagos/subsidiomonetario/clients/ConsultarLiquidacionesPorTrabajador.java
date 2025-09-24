package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.dto.ConsultaLiquidacionSubsidioMonetarioDTO;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.TipoProcesoLiquidacionEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/consultarLiquidacionesPorTrabajador
 */
public class ConsultarLiquidacionesPorTrabajador extends ServiceClient { 
   	private TipoProcesoLiquidacionEnum tipoLiquidacion;
  	private String numeroRadicacion;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Long fechaFin;
  	private Long fechaInicio;
   	private List<Long> periodo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConsultaLiquidacionSubsidioMonetarioDTO> result;
  
 	public ConsultarLiquidacionesPorTrabajador (TipoProcesoLiquidacionEnum tipoLiquidacion,String numeroRadicacion,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,Long fechaFin,Long fechaInicio,List<Long> periodo){
 		super();
		this.tipoLiquidacion=tipoLiquidacion;
		this.numeroRadicacion=numeroRadicacion;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
		this.periodo=periodo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoLiquidacion", tipoLiquidacion)
			.queryParam("numeroRadicacion", numeroRadicacion)
			.queryParam("numeroIdentificacion", numeroIdentificacion)
			.queryParam("tipoIdentificacion", tipoIdentificacion)
			.queryParam("fechaFin", fechaFin)
			.queryParam("fechaInicio", fechaInicio)
			.request(MediaType.APPLICATION_JSON)
			.post(periodo == null ? null : Entity.json(periodo));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ConsultaLiquidacionSubsidioMonetarioDTO>) response.readEntity(new GenericType<List<ConsultaLiquidacionSubsidioMonetarioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ConsultaLiquidacionSubsidioMonetarioDTO> getResult() {
		return result;
	}

 
  	public void setTipoLiquidacion (TipoProcesoLiquidacionEnum tipoLiquidacion){
 		this.tipoLiquidacion=tipoLiquidacion;
 	}
 	
 	public TipoProcesoLiquidacionEnum getTipoLiquidacion (){
 		return tipoLiquidacion;
 	}
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
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
  
  	public void setPeriodo (List<Long> periodo){
 		this.periodo=periodo;
 	}
 	
 	public List<Long> getPeriodo (){
 		return periodo;
 	}
  
}