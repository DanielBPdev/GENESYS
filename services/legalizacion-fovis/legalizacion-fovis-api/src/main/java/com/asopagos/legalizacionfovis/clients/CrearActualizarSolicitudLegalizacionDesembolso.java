package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.modelo.SolicitudLegalizacionDesembolsoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/crearActualizarSolicitudLegalizacionDesembolso
 */
public class CrearActualizarSolicitudLegalizacionDesembolso extends ServiceClient { 
    	private SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionDesembolsoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudLegalizacionDesembolsoModeloDTO result;
  
 	public CrearActualizarSolicitudLegalizacionDesembolso (SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionDesembolsoDTO){
 		super();
		this.solicitudLegalizacionDesembolsoDTO=solicitudLegalizacionDesembolsoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudLegalizacionDesembolsoDTO == null ? null : Entity.json(solicitudLegalizacionDesembolsoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudLegalizacionDesembolsoModeloDTO) response.readEntity(SolicitudLegalizacionDesembolsoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudLegalizacionDesembolsoModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudLegalizacionDesembolsoDTO (SolicitudLegalizacionDesembolsoModeloDTO solicitudLegalizacionDesembolsoDTO){
 		this.solicitudLegalizacionDesembolsoDTO=solicitudLegalizacionDesembolsoDTO;
 	}
 	
 	public SolicitudLegalizacionDesembolsoModeloDTO getSolicitudLegalizacionDesembolsoDTO (){
 		return solicitudLegalizacionDesembolsoDTO;
 	}
  
}