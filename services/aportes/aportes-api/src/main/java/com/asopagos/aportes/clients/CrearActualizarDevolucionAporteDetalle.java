package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.DevolucionAporteDetalleModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearActualizarDevolucionAporteDetalle
 */
public class CrearActualizarDevolucionAporteDetalle extends ServiceClient { 
    	private DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearActualizarDevolucionAporteDetalle (DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO){
 		super();
		this.devolucionAporteDetalleDTO=devolucionAporteDetalleDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(devolucionAporteDetalleDTO == null ? null : Entity.json(devolucionAporteDetalleDTO));
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

 
  
  	public void setDevolucionAporteDetalleDTO (DevolucionAporteDetalleModeloDTO devolucionAporteDetalleDTO){
 		this.devolucionAporteDetalleDTO=devolucionAporteDetalleDTO;
 	}
 	
 	public DevolucionAporteDetalleModeloDTO getDevolucionAporteDetalleDTO (){
 		return devolucionAporteDetalleDTO;
 	}
  
}