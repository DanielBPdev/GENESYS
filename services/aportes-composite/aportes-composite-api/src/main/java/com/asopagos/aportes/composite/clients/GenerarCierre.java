package com.asopagos.aportes.composite.clients;

import com.asopagos.enumeraciones.aportes.TipoCierreEnum;
import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudCierreRecaudoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cierre/generarCierre
 */
public class GenerarCierre extends ServiceClient { 
   	private TipoCierreEnum tipoCierre;
  	private Long fechaFin;
  	private Long fechaInicio;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	//private SolicitudCierreRecaudoModeloDTO result;
	private String result;
  
 	public GenerarCierre (TipoCierreEnum tipoCierre,Long fechaFin,Long fechaInicio){
 		super();
		this.tipoCierre=tipoCierre;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {

		System.out.println(" URL Path: "+path+
		                     "Tipo cierre "+tipoCierre+
							 "fecha inicio and fecha fin "+fechaInicio+" "+fechaFin
							 );
		Response response = webTarget.path(path)
			.queryParam("tipoCierre", tipoCierre)
			.queryParam("fechaFin", fechaFin)
			.queryParam("fechaInicio", fechaInicio)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
			
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (String) response.readEntity(String.class);
	}
	

    /**
	 * Retorna el resultado del llamado al servicio
	 */
	public String getResult() {
		return result;
	}
 
  	public void setTipoCierre (TipoCierreEnum tipoCierre){
 		this.tipoCierre=tipoCierre;
 	}
 	
 	public TipoCierreEnum getTipoCierre (){
 		return tipoCierre;
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