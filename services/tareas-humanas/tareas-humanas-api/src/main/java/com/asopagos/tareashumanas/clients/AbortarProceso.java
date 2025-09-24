package com.asopagos.tareashumanas.clients;

import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/procesos/{proceso}/abortar
 */
public class AbortarProceso extends ServiceClient { 
  	private ProcesoEnum proceso;
   	private Long idInstanciaProceso;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public AbortarProceso (ProcesoEnum proceso,Long idInstanciaProceso){
 		super();
		this.proceso=proceso;
		this.idInstanciaProceso=idInstanciaProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("proceso", proceso)
			.queryParam("idInstanciaProceso", idInstanciaProceso)
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

 	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  
  	public void setIdInstanciaProceso (Long idInstanciaProceso){
 		this.idInstanciaProceso=idInstanciaProceso;
 	}
 	
 	public Long getIdInstanciaProceso (){
 		return idInstanciaProceso;
 	}
  
  
}