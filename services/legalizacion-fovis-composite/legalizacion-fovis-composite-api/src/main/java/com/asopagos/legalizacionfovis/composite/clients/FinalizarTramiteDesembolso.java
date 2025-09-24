package com.asopagos.legalizacionfovis.composite.clients;

import com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovisComposite/finalizarTramiteDesembolso
 */
public class FinalizarTramiteDesembolso extends ServiceClient { 
    	private SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudLegalizacionDesembolsoDTO result;
  
 	public FinalizarTramiteDesembolso (SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso){
 		super();
		this.solicitudLegalizacionDesembolso=solicitudLegalizacionDesembolso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudLegalizacionDesembolso == null ? null : Entity.json(solicitudLegalizacionDesembolso));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudLegalizacionDesembolsoDTO) response.readEntity(SolicitudLegalizacionDesembolsoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudLegalizacionDesembolsoDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudLegalizacionDesembolso (SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso){
 		this.solicitudLegalizacionDesembolso=solicitudLegalizacionDesembolso;
 	}
 	
 	public SolicitudLegalizacionDesembolsoDTO getSolicitudLegalizacionDesembolso (){
 		return solicitudLegalizacionDesembolso;
 	}
  
}