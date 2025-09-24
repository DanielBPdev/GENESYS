package com.asopagos.bandejainconsistencias.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.Short;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.InconsistenciaRegistroAporteDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pilaBandeja/consultarPlanillasPorAprobarConInconsistenciasValidacion
 */
public class ConsultarPlanillasPorAprobarConInconsistenciasValidacion extends ServiceClient {
 
  
  	private String numeroIdentificacionAportante;
  	private TipoIdentificacionEnum tipoIdentificacionAportante;
  	private Long fechaFin;
  	private Short digitoVerificacionAportante;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InconsistenciaRegistroAporteDTO> result;
  
 	public ConsultarPlanillasPorAprobarConInconsistenciasValidacion (String numeroIdentificacionAportante,TipoIdentificacionEnum tipoIdentificacionAportante,Long fechaFin,Short digitoVerificacionAportante,Long fechaInicio){
 		super();
		this.numeroIdentificacionAportante=numeroIdentificacionAportante;
		this.tipoIdentificacionAportante=tipoIdentificacionAportante;
		this.fechaFin=fechaFin;
		this.digitoVerificacionAportante=digitoVerificacionAportante;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdentificacionAportante", numeroIdentificacionAportante)
						.queryParam("tipoIdentificacionAportante", tipoIdentificacionAportante)
						.queryParam("fechaFin", fechaFin)
						.queryParam("digitoVerificacionAportante", digitoVerificacionAportante)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<InconsistenciaRegistroAporteDTO>) response.readEntity(new GenericType<List<InconsistenciaRegistroAporteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<InconsistenciaRegistroAporteDTO> getResult() {
		return result;
	}

 
  	public void setNumeroIdentificacionAportante (String numeroIdentificacionAportante){
 		this.numeroIdentificacionAportante=numeroIdentificacionAportante;
 	}
 	
 	public String getNumeroIdentificacionAportante (){
 		return numeroIdentificacionAportante;
 	}
  	public void setTipoIdentificacionAportante (TipoIdentificacionEnum tipoIdentificacionAportante){
 		this.tipoIdentificacionAportante=tipoIdentificacionAportante;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionAportante (){
 		return tipoIdentificacionAportante;
 	}
  	public void setFechaFin (Long fechaFin){
 		this.fechaFin=fechaFin;
 	}
 	
 	public Long getFechaFin (){
 		return fechaFin;
 	}
  	public void setDigitoVerificacionAportante (Short digitoVerificacionAportante){
 		this.digitoVerificacionAportante=digitoVerificacionAportante;
 	}
 	
 	public Short getDigitoVerificacionAportante (){
 		return digitoVerificacionAportante;
 	}
  	public void setFechaInicio (Long fechaInicio){
 		this.fechaInicio=fechaInicio;
 	}
 	
 	public Long getFechaInicio (){
 		return fechaInicio;
 	}
  
}