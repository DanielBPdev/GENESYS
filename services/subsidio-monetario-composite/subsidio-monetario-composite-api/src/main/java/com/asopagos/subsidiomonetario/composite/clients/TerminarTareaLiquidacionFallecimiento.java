package com.asopagos.subsidiomonetario.composite.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetarioComposite/terminarTarea/liquidacionFallecimiento/{numeroRadicacion}/{idTarea}
 */
public class TerminarTareaLiquidacionFallecimiento extends ServiceClient { 
  	private String numeroRadicacion;
  	private String idTarea;
    
  
 	public TerminarTareaLiquidacionFallecimiento (String numeroRadicacion,String idTarea){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.idTarea=idTarea;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.resolveTemplate("idTarea", idTarea)
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
  	public void setIdTarea (String idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public String getIdTarea (){
 		return idTarea;
 	}
  
  
  
}