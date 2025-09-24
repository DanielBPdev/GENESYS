package com.asopagos.legalizacionfovis.composite.clients;

import com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO;
import com.asopagos.dto.modelo.SolicitudLegalizacionDesembolsoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovisComposite/reintentarTransaccionDesembolso
 */
public class ReintentarTransaccionDesembolso extends ServiceClient { 
    	private SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudLegalizacionDesembolsoModeloDTO result;
  
 	public ReintentarTransaccionDesembolso (SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso){
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
		result = (SolicitudLegalizacionDesembolsoModeloDTO) response.readEntity(SolicitudLegalizacionDesembolsoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudLegalizacionDesembolsoModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudLegalizacionDesembolso (SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolso){
 		this.solicitudLegalizacionDesembolso=solicitudLegalizacionDesembolso;
 	}
 	
 	public SolicitudLegalizacionDesembolsoDTO getSolicitudLegalizacionDesembolso (){
 		return solicitudLegalizacionDesembolso;
 	}
  
}