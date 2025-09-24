package com.asopagos.reportes.clients;

import com.asopagos.enumeraciones.reportes.ReporteNormativoEnum;
import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/reportesNormativos/verificarReportePeriodo/{tipoReporteNormativo}
 */
public class VerificarReportePeriodo extends ServiceClient {
 
  	private ReporteNormativoEnum tipoReporteNormativo;
  
  	private Long fechaFinal;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public VerificarReportePeriodo (ReporteNormativoEnum tipoReporteNormativo,Long fechaFinal,Long fechaInicio){
 		super();
		this.tipoReporteNormativo=tipoReporteNormativo;
		this.fechaFinal=fechaFinal;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("tipoReporteNormativo", tipoReporteNormativo)
									.queryParam("fechaFinal", fechaFinal)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Boolean getResult() {
		return result;
	}

 	public void setTipoReporteNormativo (ReporteNormativoEnum tipoReporteNormativo){
 		this.tipoReporteNormativo=tipoReporteNormativo;
 	}
 	
 	public ReporteNormativoEnum getTipoReporteNormativo (){
 		return tipoReporteNormativo;
 	}
  
  	public void setFechaFinal (Long fechaFinal){
 		this.fechaFinal=fechaFinal;
 	}
 	
 	public Long getFechaFinal (){
 		return fechaFinal;
 	}
  	public void setFechaInicio (Long fechaInicio){
 		this.fechaInicio=fechaInicio;
 	}
 	
 	public Long getFechaInicio (){
 		return fechaInicio;
 	}
  
}