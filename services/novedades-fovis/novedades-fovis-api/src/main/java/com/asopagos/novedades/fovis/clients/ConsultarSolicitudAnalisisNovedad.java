package com.asopagos.novedades.fovis.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudAnalisisNovedadFOVISModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedadesFovis/consultarSolicitudAnalisisNovedad
 */
public class ConsultarSolicitudAnalisisNovedad extends ServiceClient {
 
  
  	private Long idSolicitud;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAnalisisNovedadFOVISModeloDTO result;
  
 	public ConsultarSolicitudAnalisisNovedad (Long idSolicitud){
 		super();
		this.idSolicitud=idSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitud", idSolicitud)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudAnalisisNovedadFOVISModeloDTO) response.readEntity(SolicitudAnalisisNovedadFOVISModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudAnalisisNovedadFOVISModeloDTO getResult() {
		return result;
	}

 
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
}