package com.asopagos.reportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.reportes.dto.DatosIdentificadorGrupoReporteDTO;
import com.asopagos.enumeraciones.reportes.FrecuenciaMetaEnum;
import com.asopagos.enumeraciones.reportes.ReporteKPIEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/reportes/consultarIdentificadorReporte
 */
public class ConsultarIdentificadorReporte extends ServiceClient {
 
  
  	private FrecuenciaMetaEnum frecuenciaReporte;
  	private ReporteKPIEnum nombreReporte;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DatosIdentificadorGrupoReporteDTO> result;
  
 	public ConsultarIdentificadorReporte (FrecuenciaMetaEnum frecuenciaReporte,ReporteKPIEnum nombreReporte){
 		super();
		this.frecuenciaReporte=frecuenciaReporte;
		this.nombreReporte=nombreReporte;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("frecuenciaReporte", frecuenciaReporte)
						.queryParam("nombreReporte", nombreReporte)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DatosIdentificadorGrupoReporteDTO>) response.readEntity(new GenericType<List<DatosIdentificadorGrupoReporteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DatosIdentificadorGrupoReporteDTO> getResult() {
		return result;
	}

 
  	public void setFrecuenciaReporte (FrecuenciaMetaEnum frecuenciaReporte){
 		this.frecuenciaReporte=frecuenciaReporte;
 	}
 	
 	public FrecuenciaMetaEnum getFrecuenciaReporte (){
 		return frecuenciaReporte;
 	}
  	public void setNombreReporte (ReporteKPIEnum nombreReporte){
 		this.nombreReporte=nombreReporte;
 	}
 	
 	public ReporteKPIEnum getNombreReporte (){
 		return nombreReporte;
 	}
  
}