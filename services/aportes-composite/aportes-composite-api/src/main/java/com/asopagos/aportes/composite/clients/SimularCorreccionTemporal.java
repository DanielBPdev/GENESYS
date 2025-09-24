package com.asopagos.aportes.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.aportes.CotizanteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/{idSolicitud}/simularCorreccionTemporal
 */
public class SimularCorreccionTemporal extends ServiceClient { 
  	private Long idSolicitud;
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CotizanteDTO> result;
  
 	public SimularCorreccionTemporal (Long idSolicitud){
 		super();
		this.idSolicitud=idSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitud", idSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<CotizanteDTO>) response.readEntity(new GenericType<List<CotizanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<CotizanteDTO> getResult() {
		return result;
	}

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  
  
}