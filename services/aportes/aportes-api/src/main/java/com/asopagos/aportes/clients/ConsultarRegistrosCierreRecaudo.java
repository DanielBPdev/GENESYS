package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.aportes.dto.ResumenCierreRecaudoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarRegistrosCierreRecaudo
 */
public class ConsultarRegistrosCierreRecaudo extends ServiceClient { 
   	private Long fechaFin;
  	private Long fechaInicio;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ResumenCierreRecaudoDTO> result;
  
 	public ConsultarRegistrosCierreRecaudo (Long fechaFin,Long fechaInicio){
 		super();
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		System.out.println("String path "+path);
		Response response = webTarget.path(path)
			.queryParam("fechaFin", fechaFin)
			.queryParam("fechaInicio", fechaInicio)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ResumenCierreRecaudoDTO>) response.readEntity(new GenericType<List<ResumenCierreRecaudoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ResumenCierreRecaudoDTO> getResult() {
		return result;
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