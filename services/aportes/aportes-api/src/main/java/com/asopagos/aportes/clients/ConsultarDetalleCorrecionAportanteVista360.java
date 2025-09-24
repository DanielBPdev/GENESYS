package com.asopagos.aportes.clients;

import java.lang.Long;
import com.asopagos.aportes.dto.DetalleCorreccionAportanteVista360DTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarDetalleCorrecionAportanteVista360/{idSolicitudCorreccion}
 */
public class ConsultarDetalleCorrecionAportanteVista360 extends ServiceClient {
 
  	private Long idSolicitudCorreccion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DetalleCorreccionAportanteVista360DTO result;
  
 	public ConsultarDetalleCorrecionAportanteVista360 (Long idSolicitudCorreccion){
 		super();
		this.idSolicitudCorreccion=idSolicitudCorreccion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitudCorreccion", idSolicitudCorreccion)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DetalleCorreccionAportanteVista360DTO) response.readEntity(DetalleCorreccionAportanteVista360DTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DetalleCorreccionAportanteVista360DTO getResult() {
		return result;
	}

 	public void setIdSolicitudCorreccion (Long idSolicitudCorreccion){
 		this.idSolicitudCorreccion=idSolicitudCorreccion;
 	}
 	
 	public Long getIdSolicitudCorreccion (){
 		return idSolicitudCorreccion;
 	}
  
  
}