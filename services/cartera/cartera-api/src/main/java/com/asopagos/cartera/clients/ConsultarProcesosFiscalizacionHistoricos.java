package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.cartera.EstadoCicloCarteraEnum;
import java.lang.Long;
import com.asopagos.dto.modelo.CicloCarteraModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarProcesosFiscalizacionHistoricos
 */
public class ConsultarProcesosFiscalizacionHistoricos extends ServiceClient {
 
  
  	private EstadoCicloCarteraEnum estadoCicloFiscalizacion;
  	private Long idCicloFiscalizacion;
  	private Long fechaFin;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CicloCarteraModeloDTO> result;
  
 	public ConsultarProcesosFiscalizacionHistoricos (EstadoCicloCarteraEnum estadoCicloFiscalizacion,Long idCicloFiscalizacion,Long fechaFin,Long fechaInicio){
 		super();
		this.estadoCicloFiscalizacion=estadoCicloFiscalizacion;
		this.idCicloFiscalizacion=idCicloFiscalizacion;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("estadoCicloFiscalizacion", estadoCicloFiscalizacion)
						.queryParam("idCicloFiscalizacion", idCicloFiscalizacion)
						.queryParam("fechaFin", fechaFin)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CicloCarteraModeloDTO>) response.readEntity(new GenericType<List<CicloCarteraModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CicloCarteraModeloDTO> getResult() {
		return result;
	}

 
  	public void setEstadoCicloFiscalizacion (EstadoCicloCarteraEnum estadoCicloFiscalizacion){
 		this.estadoCicloFiscalizacion=estadoCicloFiscalizacion;
 	}
 	
 	public EstadoCicloCarteraEnum getEstadoCicloFiscalizacion (){
 		return estadoCicloFiscalizacion;
 	}
  	public void setIdCicloFiscalizacion (Long idCicloFiscalizacion){
 		this.idCicloFiscalizacion=idCicloFiscalizacion;
 	}
 	
 	public Long getIdCicloFiscalizacion (){
 		return idCicloFiscalizacion;
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