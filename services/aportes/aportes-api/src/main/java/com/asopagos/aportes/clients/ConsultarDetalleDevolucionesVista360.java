package com.asopagos.aportes.clients;

import java.lang.Long;
import com.asopagos.aportes.dto.DetalleDevolucionVista360DTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarDetalleDevolucionesVista360
 */
public class ConsultarDetalleDevolucionesVista360 extends ServiceClient {
 
  
  	private Long idSolicitudDevolucion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DetalleDevolucionVista360DTO result;
  
 	public ConsultarDetalleDevolucionesVista360 (Long idSolicitudDevolucion){
 		super();
		this.idSolicitudDevolucion=idSolicitudDevolucion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitudDevolucion", idSolicitudDevolucion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DetalleDevolucionVista360DTO) response.readEntity(DetalleDevolucionVista360DTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DetalleDevolucionVista360DTO getResult() {
		return result;
	}

 
  	public void setIdSolicitudDevolucion (Long idSolicitudDevolucion){
 		this.idSolicitudDevolucion=idSolicitudDevolucion;
 	}
 	
 	public Long getIdSolicitudDevolucion (){
 		return idSolicitudDevolucion;
 	}
  
}