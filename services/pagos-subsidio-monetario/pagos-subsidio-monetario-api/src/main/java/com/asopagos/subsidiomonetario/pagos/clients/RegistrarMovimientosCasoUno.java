package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.ResultadoReexpedicionBloqueoInDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/registrarMovimientosCasoUno
 */
public class RegistrarMovimientosCasoUno extends ServiceClient { 
   	private Long idPersona;
   	private ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion;
  
  
 	public RegistrarMovimientosCasoUno (Long idPersona,ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion){
 		super();
		this.idPersona=idPersona;
		this.resultadoReexpedicion=resultadoReexpedicion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idPersona", idPersona)
			.request(MediaType.APPLICATION_JSON)
			.post(resultadoReexpedicion == null ? null : Entity.json(resultadoReexpedicion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  
  	public void setResultadoReexpedicion (ResultadoReexpedicionBloqueoInDTO resultadoReexpedicion){
 		this.resultadoReexpedicion=resultadoReexpedicion;
 	}
 	
 	public ResultadoReexpedicionBloqueoInDTO getResultadoReexpedicion (){
 		return resultadoReexpedicion;
 	}
  
}