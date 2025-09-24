package com.asopagos.fovis.clients;

import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.fovis.ResultadoHistoricoSolicitudesFovisDTO;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudLegalizacionDesembolsoEnum;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudNovedadFovisEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarHistoricoSolicitudesFOVIS
 */
public class ConsultarHistoricoSolicitudesFOVIS extends ServiceClient {
 
  
  	private EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion;
  	private EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitudlegalizacion;
  	private String numeroSolicitud;
  	private Long fechaExactaRadicacion;
  	private TipoSolicitudEnum tipoSolicitud;
  	private Long idPostulacion;
  	private EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad;
  	private Long fechaFin;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ResultadoHistoricoSolicitudesFovisDTO> result;
  
 	public ConsultarHistoricoSolicitudesFOVIS (EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion,EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitudlegalizacion,String numeroSolicitud,Long fechaExactaRadicacion,TipoSolicitudEnum tipoSolicitud,Long idPostulacion,EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad,Long fechaFin,Long fechaInicio){
 		super();
		this.estadoSolicitudPostulacion=estadoSolicitudPostulacion;
		this.estadoSolicitudlegalizacion=estadoSolicitudlegalizacion;
		this.numeroSolicitud=numeroSolicitud;
		this.fechaExactaRadicacion=fechaExactaRadicacion;
		this.tipoSolicitud=tipoSolicitud;
		this.idPostulacion=idPostulacion;
		this.estadoSolicitudNovedad=estadoSolicitudNovedad;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("estadoSolicitudPostulacion", estadoSolicitudPostulacion)
						.queryParam("estadoSolicitudlegalizacion", estadoSolicitudlegalizacion)
						.queryParam("numeroSolicitud", numeroSolicitud)
						.queryParam("fechaExactaRadicacion", fechaExactaRadicacion)
						.queryParam("tipoSolicitud", tipoSolicitud)
						.queryParam("idPostulacion", idPostulacion)
						.queryParam("estadoSolicitudNovedad", estadoSolicitudNovedad)
						.queryParam("fechaFin", fechaFin)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ResultadoHistoricoSolicitudesFovisDTO>) response.readEntity(new GenericType<List<ResultadoHistoricoSolicitudesFovisDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ResultadoHistoricoSolicitudesFovisDTO> getResult() {
		return result;
	}

 
  	public void setEstadoSolicitudPostulacion (EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion){
 		this.estadoSolicitudPostulacion=estadoSolicitudPostulacion;
 	}
 	
 	public EstadoSolicitudPostulacionEnum getEstadoSolicitudPostulacion (){
 		return estadoSolicitudPostulacion;
 	}
  	public void setEstadoSolicitudlegalizacion (EstadoSolicitudLegalizacionDesembolsoEnum estadoSolicitudlegalizacion){
 		this.estadoSolicitudlegalizacion=estadoSolicitudlegalizacion;
 	}
 	
 	public EstadoSolicitudLegalizacionDesembolsoEnum getEstadoSolicitudlegalizacion (){
 		return estadoSolicitudlegalizacion;
 	}
  	public void setNumeroSolicitud (String numeroSolicitud){
 		this.numeroSolicitud=numeroSolicitud;
 	}
 	
 	public String getNumeroSolicitud (){
 		return numeroSolicitud;
 	}
  	public void setFechaExactaRadicacion (Long fechaExactaRadicacion){
 		this.fechaExactaRadicacion=fechaExactaRadicacion;
 	}
 	
 	public Long getFechaExactaRadicacion (){
 		return fechaExactaRadicacion;
 	}
  	public void setTipoSolicitud (TipoSolicitudEnum tipoSolicitud){
 		this.tipoSolicitud=tipoSolicitud;
 	}
 	
 	public TipoSolicitudEnum getTipoSolicitud (){
 		return tipoSolicitud;
 	}
  	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  	public void setEstadoSolicitudNovedad (EstadoSolicitudNovedadFovisEnum estadoSolicitudNovedad){
 		this.estadoSolicitudNovedad=estadoSolicitudNovedad;
 	}
 	
 	public EstadoSolicitudNovedadFovisEnum getEstadoSolicitudNovedad (){
 		return estadoSolicitudNovedad;
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