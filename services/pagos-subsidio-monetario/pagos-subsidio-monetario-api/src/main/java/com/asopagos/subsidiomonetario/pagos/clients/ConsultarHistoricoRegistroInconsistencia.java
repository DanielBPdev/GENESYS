package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoResolucionInconsistenciaEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.TipoNovedadInconsistenciaEnum;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroInconsistenciaTarjeta;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarHistoricoRegistroInconsistencia
 */
public class ConsultarHistoricoRegistroInconsistencia extends ServiceClient {
 
  
  	private Long fechaInicial;
  	private String numeroId;
  	private EstadoResolucionInconsistenciaEnum estadoResolucion;
  	private TipoNovedadInconsistenciaEnum tipoNovedad;
  	private Long fechaFinal;
  	private TipoIdentificacionEnum tipoId;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RegistroInconsistenciaTarjeta> result;
  
 	public ConsultarHistoricoRegistroInconsistencia (Long fechaInicial,String numeroId,EstadoResolucionInconsistenciaEnum estadoResolucion,TipoNovedadInconsistenciaEnum tipoNovedad,Long fechaFinal,TipoIdentificacionEnum tipoId){
 		super();
		this.fechaInicial=fechaInicial;
		this.numeroId=numeroId;
		this.estadoResolucion=estadoResolucion;
		this.tipoNovedad=tipoNovedad;
		this.fechaFinal=fechaFinal;
		this.tipoId=tipoId;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("fechaInicial", fechaInicial)
						.queryParam("numeroId", numeroId)
						.queryParam("estadoResolucion", estadoResolucion)
						.queryParam("tipoNovedad", tipoNovedad)
						.queryParam("fechaFinal", fechaFinal)
						.queryParam("tipoId", tipoId)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RegistroInconsistenciaTarjeta>) response.readEntity(new GenericType<List<RegistroInconsistenciaTarjeta>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RegistroInconsistenciaTarjeta> getResult() {
		return result;
	}

 
  	public void setFechaInicial (Long fechaInicial){
 		this.fechaInicial=fechaInicial;
 	}
 	
 	public Long getFechaInicial (){
 		return fechaInicial;
 	}
  	public void setNumeroId (String numeroId){
 		this.numeroId=numeroId;
 	}
 	
 	public String getNumeroId (){
 		return numeroId;
 	}
  	public void setEstadoResolucion (EstadoResolucionInconsistenciaEnum estadoResolucion){
 		this.estadoResolucion=estadoResolucion;
 	}
 	
 	public EstadoResolucionInconsistenciaEnum getEstadoResolucion (){
 		return estadoResolucion;
 	}
  	public void setTipoNovedad (TipoNovedadInconsistenciaEnum tipoNovedad){
 		this.tipoNovedad=tipoNovedad;
 	}
 	
 	public TipoNovedadInconsistenciaEnum getTipoNovedad (){
 		return tipoNovedad;
 	}
  	public void setFechaFinal (Long fechaFinal){
 		this.fechaFinal=fechaFinal;
 	}
 	
 	public Long getFechaFinal (){
 		return fechaFinal;
 	}
  	public void setTipoId (TipoIdentificacionEnum tipoId){
 		this.tipoId=tipoId;
 	}
 	
 	public TipoIdentificacionEnum getTipoId (){
 		return tipoId;
 	}
  
}