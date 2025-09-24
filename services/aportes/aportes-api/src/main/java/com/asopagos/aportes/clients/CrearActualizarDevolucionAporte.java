package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.DevolucionAporteModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearActualizarDevolucionAporte
 */
public class CrearActualizarDevolucionAporte extends ServiceClient { 
    	private DevolucionAporteModeloDTO devolucionAporteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearActualizarDevolucionAporte (DevolucionAporteModeloDTO devolucionAporteDTO){
 		super();
		this.devolucionAporteDTO=devolucionAporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(devolucionAporteDTO == null ? null : Entity.json(devolucionAporteDTO));
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

 
  
  	public void setDevolucionAporteDTO (DevolucionAporteModeloDTO devolucionAporteDTO){
 		this.devolucionAporteDTO=devolucionAporteDTO;
 	}
 	
 	public DevolucionAporteModeloDTO getDevolucionAporteDTO (){
 		return devolucionAporteDTO;
 	}
  
}