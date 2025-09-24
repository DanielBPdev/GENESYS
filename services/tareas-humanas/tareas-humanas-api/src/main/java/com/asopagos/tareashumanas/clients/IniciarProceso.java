package com.asopagos.tareashumanas.clients;

import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.lang.Long;
import java.util.Map;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/procesos/{proceso}/iniciarProceso
 */
public class IniciarProceso extends ServiceClient { 
  	private ProcesoEnum proceso;
    	private Map<String,Object> params;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public IniciarProceso (ProcesoEnum proceso,Map<String,Object> params){
 		super();
		this.proceso=proceso;
		this.params=params;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("proceso", proceso)
			.request(MediaType.APPLICATION_JSON)
			.post(params == null ? null : Entity.json(params));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  
  
  	public void setParams (Map<String,Object> params){
 		this.params=params;
 	}
 	
 	public Map<String,Object> getParams (){
 		return params;
 	}
  
}