package com.asopagos.aportes.masivos.clients;
import java.util.List;
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
public class SimularDevolucionTemporalMasiva extends ServiceClient { 
  	private Long idSolicitudGlobal;
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DevolucionDTO> result;
  
 	public SimularDevolucionTemporalMasiva (Long idSolicitudGlobal){
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
		result = (List<DevolucionDTO>) response.readEntity(DevolucionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<DevolucionDTO> getResult() {
		return result;
	}

 	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  
  
}