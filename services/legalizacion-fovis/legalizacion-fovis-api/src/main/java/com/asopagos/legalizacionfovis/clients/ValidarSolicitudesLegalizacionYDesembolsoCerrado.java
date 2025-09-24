package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.modelo.SolicitudLegalizacionDesembolsoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/validarSolicitudesLegalizacionYDesembolsoCerrado
 */
public class ValidarSolicitudesLegalizacionYDesembolsoCerrado extends ServiceClient { 
	
	private Long idPostulacionFOVIS;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarSolicitudesLegalizacionYDesembolsoCerrado (Long idPostulacionFOVIS){
 		super();
		this.idPostulacionFOVIS=idPostulacionFOVIS;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idPostulacionFOVIS == null ? null : Entity.json(idPostulacionFOVIS));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}


	public Long getIdPostulacionFOVIS() {
		return this.idPostulacionFOVIS;
	}

	public void setIdPostulacionFOVIS(Long idPostulacionFOVIS) {
		this.idPostulacionFOVIS = idPostulacionFOVIS;
	}
  
}