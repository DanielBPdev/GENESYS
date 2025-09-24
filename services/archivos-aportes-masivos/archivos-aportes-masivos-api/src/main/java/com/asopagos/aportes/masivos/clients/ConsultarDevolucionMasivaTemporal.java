package com.asopagos.aportes.masivos.clients;

import java.lang.Long;
import java.util.List;
import java.util.ArrayList;
import com.asopagos.aportes.composite.dto.DevolucionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aporteManual/{idSolicitud}/consultarDevolucionTemporal
 */
public class ConsultarDevolucionMasivaTemporal extends ServiceClient {
 
  	private List<Long> idSolicitud;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DevolucionDTO> result;
  
 	public ConsultarDevolucionMasivaTemporal (List<Long> idSolicitud){
 		super();
		this.idSolicitud=idSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitud", idSolicitud)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DevolucionDTO>) response.readEntity(DevolucionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DevolucionDTO> getResult() {
		return result;
	}

 	public void setIdSolicitud (List<Long> idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public List<Long> getIdSolicitud (){
 		return idSolicitud;
 	}
  
  
}
