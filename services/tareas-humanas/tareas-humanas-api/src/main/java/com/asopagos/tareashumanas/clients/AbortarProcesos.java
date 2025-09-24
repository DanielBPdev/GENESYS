package com.asopagos.tareashumanas.clients;

import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/procesos/{proceso}/abortarMultiplesProcesos
 */
public class AbortarProcesos extends ServiceClient { 
  	private ProcesoEnum proceso;
    	private List<Long> idInstanciaProceso;
  
  
 	public AbortarProcesos (ProcesoEnum proceso,List<Long> idInstanciaProceso){
 		super();
		this.proceso=proceso;
		this.idInstanciaProceso=idInstanciaProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("proceso", proceso)
			.request(MediaType.APPLICATION_JSON)
			.post(idInstanciaProceso == null ? null : Entity.json(idInstanciaProceso));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  
  
  	public void setIdInstanciaProceso (List<Long> idInstanciaProceso){
 		this.idInstanciaProceso=idInstanciaProceso;
 	}
 	
 	public List<Long> getIdInstanciaProceso (){
 		return idInstanciaProceso;
 	}
  
}