package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.dto.CargueBloqueoCMDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/radicarCargueManualBloqueoCM
 */
public class RadicarCargueManualBloqueoCM extends ServiceClient { 
    	private CargueBloqueoCMDTO cargue;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RadicarCargueManualBloqueoCM (CargueBloqueoCMDTO cargue){
 		super();
		this.cargue=cargue;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(cargue == null ? null : Entity.json(cargue));
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

 
  
  	public void setCargue (CargueBloqueoCMDTO cargue){
 		this.cargue=cargue;
 	}
 	
 	public CargueBloqueoCMDTO getCargue (){
 		return cargue;
 	}
  
}