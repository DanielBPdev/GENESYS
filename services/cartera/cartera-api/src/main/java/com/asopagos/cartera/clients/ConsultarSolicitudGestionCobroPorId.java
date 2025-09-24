package com.asopagos.cartera.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudGestionCobroFisicoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarSolicitudGestionCobroPorId
 */
public class ConsultarSolicitudGestionCobroPorId extends ServiceClient {
 
  
  	private Long idSolicitudPrimeraRemision;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudGestionCobroFisicoModeloDTO result;
  
 	public ConsultarSolicitudGestionCobroPorId (Long idSolicitudPrimeraRemision){
 		super();
		this.idSolicitudPrimeraRemision=idSolicitudPrimeraRemision;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitudPrimeraRemision", idSolicitudPrimeraRemision)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudGestionCobroFisicoModeloDTO) response.readEntity(SolicitudGestionCobroFisicoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudGestionCobroFisicoModeloDTO getResult() {
		return result;
	}

 
  	public void setIdSolicitudPrimeraRemision (Long idSolicitudPrimeraRemision){
 		this.idSolicitudPrimeraRemision=idSolicitudPrimeraRemision;
 	}
 	
 	public Long getIdSolicitudPrimeraRemision (){
 		return idSolicitudPrimeraRemision;
 	}
  
}