package com.asopagos.bandejainconsistencias.clients;

import java.lang.Long;
import com.asopagos.bandejainconsistencias.dto.DatosBandejaTransitoriaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pilaBandeja/detalleBandejaTransitoriaGestion
 */
public class DetalleBandejaTransitoriaGestion extends ServiceClient {
 
  
  	private Long idPilaEstadoTransitorio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatosBandejaTransitoriaDTO result;
  
 	public DetalleBandejaTransitoriaGestion (Long idPilaEstadoTransitorio){
 		super();
		this.idPilaEstadoTransitorio=idPilaEstadoTransitorio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPilaEstadoTransitorio", idPilaEstadoTransitorio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DatosBandejaTransitoriaDTO) response.readEntity(DatosBandejaTransitoriaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DatosBandejaTransitoriaDTO getResult() {
		return result;
	}

 
  	public void setIdPilaEstadoTransitorio (Long idPilaEstadoTransitorio){
 		this.idPilaEstadoTransitorio=idPilaEstadoTransitorio;
 	}
 	
 	public Long getIdPilaEstadoTransitorio (){
 		return idPilaEstadoTransitorio;
 	}
  
}