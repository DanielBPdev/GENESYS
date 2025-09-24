package com.asopagos.aportes.clients;

import java.util.List;
import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/actualizacionAportesRecalculados
 */
public class ActualizacionAportesRecalculados extends ServiceClient { 
   	private Boolean procesoManual;
   	private List<Long> idsRegistrosOrigen;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ActualizacionAportesRecalculados (Boolean procesoManual,List<Long> idsRegistrosOrigen){
 		super();
		this.procesoManual=procesoManual;
		this.idsRegistrosOrigen=idsRegistrosOrigen;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("procesoManual", procesoManual)
			.request(MediaType.APPLICATION_JSON)
			.post(idsRegistrosOrigen == null ? null : Entity.json(idsRegistrosOrigen));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  	public void setProcesoManual (Boolean procesoManual){
 		this.procesoManual=procesoManual;
 	}
 	
 	public Boolean getProcesoManual (){
 		return procesoManual;
 	}
  
  	public void setIdsRegistrosOrigen (List<Long> idsRegistrosOrigen){
 		this.idsRegistrosOrigen=idsRegistrosOrigen;
 	}
 	
 	public List<Long> getIdsRegistrosOrigen (){
 		return idsRegistrosOrigen;
 	}
  
}