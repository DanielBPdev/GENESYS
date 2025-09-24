package com.asopagos.subsidiomonetario.composite.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetarioComposite/ejecutar/accionesGestion/liquidacionFallecimiento/{numeroRadicacion}
 */
public class EjecutarAccionesGestionLiquidacionFallecimiento extends ServiceClient { 
  	private String numeroRadicacion;
   	private Long idCondicionPersona;
   
  
 	public EjecutarAccionesGestionLiquidacionFallecimiento (String numeroRadicacion,Long idCondicionPersona){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.idCondicionPersona=idCondicionPersona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.queryParam("idCondicionPersona", idCondicionPersona)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
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
  
  	public void setIdCondicionPersona (Long idCondicionPersona){
 		this.idCondicionPersona=idCondicionPersona;
 	}
 	
 	public Long getIdCondicionPersona (){
 		return idCondicionPersona;
 	}
  
  
}