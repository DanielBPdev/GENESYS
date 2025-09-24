package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import com.asopagos.aportes.composite.dto.DevolucionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/simularDevolucion/{idSolicitudGlobal}
 */
public class SimularDevolucionTemporal extends ServiceClient { 
  	private Long idSolicitudGlobal;
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DevolucionDTO result;
  
 	public SimularDevolucionTemporal (Long idSolicitudGlobal){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudGlobal", idSolicitudGlobal)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (DevolucionDTO) response.readEntity(DevolucionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DevolucionDTO getResult() {
		return result;
	}

 	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  
  
}