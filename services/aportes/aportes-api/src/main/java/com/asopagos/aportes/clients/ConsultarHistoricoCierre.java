package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.aportes.TipoCierreEnum;
import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudCierreRecaudoModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarHistoricoCierre
 */
public class ConsultarHistoricoCierre extends ServiceClient {
 
  
  	private TipoCierreEnum tipoCierre;
  	private String numeroRadicacion;
  	private Long fechaFin;
  	private Long fechaInicio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudCierreRecaudoModeloDTO> result;
  
 	public ConsultarHistoricoCierre (TipoCierreEnum tipoCierre,String numeroRadicacion,Long fechaFin,Long fechaInicio){
 		super();
		this.tipoCierre=tipoCierre;
		this.numeroRadicacion=numeroRadicacion;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoCierre", tipoCierre)
						.queryParam("numeroRadicacion", numeroRadicacion)
						.queryParam("fechaFin", fechaFin)
						.queryParam("fechaInicio", fechaInicio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudCierreRecaudoModeloDTO>) response.readEntity(new GenericType<List<SolicitudCierreRecaudoModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudCierreRecaudoModeloDTO> getResult() {
		return result;
	}

 
  	public void setTipoCierre (TipoCierreEnum tipoCierre){
 		this.tipoCierre=tipoCierre;
 	}
 	
 	public TipoCierreEnum getTipoCierre (){
 		return tipoCierre;
 	}
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
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