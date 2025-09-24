package com.asopagos.cartera.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.DetalleSolicitudGestionCobroModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{idSolicitudPrimeraRemision}/consultarDetallePorSolicitudPrimeraRemision
 */
public class ConsultarDetallePorSolicitudPrimeraRemision extends ServiceClient {
 
  	private Long idSolicitudPrimeraRemision;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DetalleSolicitudGestionCobroModeloDTO result;
  
 	public ConsultarDetallePorSolicitudPrimeraRemision (Long idSolicitudPrimeraRemision){
 		super();
		this.idSolicitudPrimeraRemision=idSolicitudPrimeraRemision;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitudPrimeraRemision", idSolicitudPrimeraRemision)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DetalleSolicitudGestionCobroModeloDTO) response.readEntity(DetalleSolicitudGestionCobroModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DetalleSolicitudGestionCobroModeloDTO getResult() {
		return result;
	}

 	public void setIdSolicitudPrimeraRemision (Long idSolicitudPrimeraRemision){
 		this.idSolicitudPrimeraRemision=idSolicitudPrimeraRemision;
 	}
 	
 	public Long getIdSolicitudPrimeraRemision (){
 		return idSolicitudPrimeraRemision;
 	}
  
  
}