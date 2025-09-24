package com.asopagos.fovis.clients;

import com.asopagos.enumeraciones.afiliaciones.TipoSolicitudEnum;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.fovis.EstadoSolicitudPostulacionEnum;
import java.lang.Long;
import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarHistoricoSolicitudesPostulacion
 */
public class ConsultarHistoricoSolicitudesPostulacion extends ServiceClient {
 
  
  	private EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion;
  	private String numeroSolicitud;
  	private Long fechaExactaRadicacion;
  	private TipoSolicitudEnum tipoSolicitud;
  	private Long fechaFin;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudPostulacionFOVISDTO> result;
  
 	public ConsultarHistoricoSolicitudesPostulacion (EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion,String numeroSolicitud,Long fechaExactaRadicacion,TipoSolicitudEnum tipoSolicitud,Long fechaFin,Long fechaInicio){
 		super();
		this.estadoSolicitudPostulacion=estadoSolicitudPostulacion;
		this.numeroSolicitud=numeroSolicitud;
		this.fechaExactaRadicacion=fechaExactaRadicacion;
		this.tipoSolicitud=tipoSolicitud;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("estadoSolicitudPostulacion", estadoSolicitudPostulacion)
						.queryParam("numeroSolicitud", numeroSolicitud)
						.queryParam("fechaExactaRadicacion", fechaExactaRadicacion)
						.queryParam("tipoSolicitud", tipoSolicitud)
						.queryParam("fechaFin", fechaFin)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudPostulacionFOVISDTO>) response.readEntity(new GenericType<List<SolicitudPostulacionFOVISDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudPostulacionFOVISDTO> getResult() {
		return result;
	}

 
  	public void setEstadoSolicitudPostulacion (EstadoSolicitudPostulacionEnum estadoSolicitudPostulacion){
 		this.estadoSolicitudPostulacion=estadoSolicitudPostulacion;
 	}
 	
 	public EstadoSolicitudPostulacionEnum getEstadoSolicitudPostulacion (){
 		return estadoSolicitudPostulacion;
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