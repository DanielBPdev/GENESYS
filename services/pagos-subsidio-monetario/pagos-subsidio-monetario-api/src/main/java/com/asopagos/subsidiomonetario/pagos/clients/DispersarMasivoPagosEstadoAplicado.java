package com.asopagos.subsidiomonetario.pagos.clients;

import java.util.List;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/dispersarMasivoPagosEstadoAplicado
 */
public class DispersarMasivoPagosEstadoAplicado extends ServiceClient { 
   	private String numeroRadicacion;
   	private List<Long> abonosExitosos;
  
  
 	public DispersarMasivoPagosEstadoAplicado (String numeroRadicacion,List<Long> abonosExitosos){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.abonosExitosos=abonosExitosos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("numeroRadicacion", numeroRadicacion)
			.request(MediaType.APPLICATION_JSON)
			.post(abonosExitosos == null ? null : Entity.json(abonosExitosos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  	public void setAbonosExitosos (List<Long> abonosExitosos){
 		this.abonosExitosos=abonosExitosos;
 	}
 	
 	public List<Long> getAbonosExitosos (){
 		return abonosExitosos;
 	}
  
}