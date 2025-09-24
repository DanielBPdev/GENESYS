package com.asopagos.consola.estado.cargue.procesos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.core.TipoProcesoMasivoEnum;
import com.asopagos.enumeraciones.core.EstadoCargueMasivoEnum;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/consolaEstadoCargueProcesos/consultarCargueConsola
 */
public class ConsultarCargueConsolaEstado extends ServiceClient {
 
  
  	private TipoProcesoMasivoEnum tipoProceso;
  	private EstadoCargueMasivoEnum estado;
  	private Long fechaFin;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConsolaEstadoCargueProcesoDTO> result;
  
 	public ConsultarCargueConsolaEstado (TipoProcesoMasivoEnum tipoProceso,EstadoCargueMasivoEnum estado,Long fechaFin,Long fechaInicio){
 		super();
		this.tipoProceso=tipoProceso;
		this.estado=estado;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoProceso", tipoProceso)
						.queryParam("estado", estado)
						.queryParam("fechaFin", fechaFin)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ConsolaEstadoCargueProcesoDTO>) response.readEntity(new GenericType<List<ConsolaEstadoCargueProcesoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ConsolaEstadoCargueProcesoDTO> getResult() {
		return result;
	}

 
  	public void setTipoProceso (TipoProcesoMasivoEnum tipoProceso){
 		this.tipoProceso=tipoProceso;
 	}
 	
 	public TipoProcesoMasivoEnum getTipoProceso (){
 		return tipoProceso;
 	}
  	public void setEstado (EstadoCargueMasivoEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoCargueMasivoEnum getEstado (){
 		return estado;
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