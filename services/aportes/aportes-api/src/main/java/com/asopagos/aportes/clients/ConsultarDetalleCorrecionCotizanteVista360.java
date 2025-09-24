package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.aportes.dto.DetalleCorreccionCotizanteVista360DTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarDetalleCorrecionCotizanteVista360/{idSolicitudCorreccion}
 */
public class ConsultarDetalleCorrecionCotizanteVista360 extends ServiceClient {
 
  	private Long idSolicitudCorreccion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleCorreccionCotizanteVista360DTO> result;
  
 	public ConsultarDetalleCorrecionCotizanteVista360 (Long idSolicitudCorreccion){
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
		this.result = (List<DetalleCorreccionCotizanteVista360DTO>) response.readEntity(new GenericType<List<DetalleCorreccionCotizanteVista360DTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DetalleCorreccionCotizanteVista360DTO> getResult() {
		return result;
	}

 	public void setIdSolicitudCorreccion (Long idSolicitudCorreccion){
 		this.idSolicitudCorreccion=idSolicitudCorreccion;
 	}
 	
 	public Long getIdSolicitudCorreccion (){
 		return idSolicitudCorreccion;
 	}
  
  
}