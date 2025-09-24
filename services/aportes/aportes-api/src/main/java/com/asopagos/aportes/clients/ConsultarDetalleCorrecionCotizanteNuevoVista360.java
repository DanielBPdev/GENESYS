package com.asopagos.aportes.clients;

import com.asopagos.aportes.dto.DetalleCorreccionCotizanteNuevoVista360DTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarDetalleCorrecionCotizanteNuevoVista360/{idSolicitudCorreccion}
 */
public class ConsultarDetalleCorrecionCotizanteNuevoVista360 extends ServiceClient {
 
  	private Long idSolicitudCorreccion;
  
  	private Long idAporteDetallado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleCorreccionCotizanteNuevoVista360DTO> result;
  
 	public ConsultarDetalleCorrecionCotizanteNuevoVista360 (Long idSolicitudCorreccion,Long idAporteDetallado){
 		super();
		this.idSolicitudCorreccion=idSolicitudCorreccion;
		this.idAporteDetallado=idAporteDetallado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitudCorreccion", idSolicitudCorreccion)
									.queryParam("idAporteDetallado", idAporteDetallado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DetalleCorreccionCotizanteNuevoVista360DTO>) response.readEntity(new GenericType<List<DetalleCorreccionCotizanteNuevoVista360DTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DetalleCorreccionCotizanteNuevoVista360DTO> getResult() {
		return result;
	}

 	public void setIdSolicitudCorreccion (Long idSolicitudCorreccion){
 		this.idSolicitudCorreccion=idSolicitudCorreccion;
 	}
 	
 	public Long getIdSolicitudCorreccion (){
 		return idSolicitudCorreccion;
 	}
  
  	public void setIdAporteDetallado (Long idAporteDetallado){
 		this.idAporteDetallado=idAporteDetallado;
 	}
 	
 	public Long getIdAporteDetallado (){
 		return idAporteDetallado;
 	}
  
}