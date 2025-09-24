package com.asopagos.afiliaciones.personas.web.composite.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/seguimientoCorregirInformacion
 */
public class CorregirInformacionAfiliacion extends ServiceClient { 
   	private String resultadoGestion;
  	private Long idInstanciaProceso;
   
  
 	public CorregirInformacionAfiliacion (String resultadoGestion,Long idInstanciaProceso){
 		super();
		this.resultadoGestion=resultadoGestion;
		this.idInstanciaProceso=idInstanciaProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("resultadoGestion", resultadoGestion)
			.queryParam("idInstanciaProceso", idInstanciaProceso)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setResultadoGestion (String resultadoGestion){
 		this.resultadoGestion=resultadoGestion;
 	}
 	
 	public String getResultadoGestion (){
 		return resultadoGestion;
 	}
  	public void setIdInstanciaProceso (Long idInstanciaProceso){
 		this.idInstanciaProceso=idInstanciaProceso;
 	}
 	
 	public Long getIdInstanciaProceso (){
 		return idInstanciaProceso;
 	}
  
  
}