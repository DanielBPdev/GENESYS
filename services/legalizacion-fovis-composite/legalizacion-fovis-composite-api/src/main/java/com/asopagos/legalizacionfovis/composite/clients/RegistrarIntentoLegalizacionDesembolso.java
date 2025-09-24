package com.asopagos.legalizacionfovis.composite.clients;

import com.asopagos.dto.fovis.SolicitudLegalizacionDesembolsoDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovisComposite/registrarIntentoLegalizacionDesembolso
 */
public class RegistrarIntentoLegalizacionDesembolso extends ServiceClient { 
    	private SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarIntentoLegalizacionDesembolso (SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO){
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
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setSolicitudLegalizacionDesembolsoDTO (SolicitudLegalizacionDesembolsoDTO solicitudLegalizacionDesembolsoDTO){
 		this.solicitudLegalizacionDesembolsoDTO=solicitudLegalizacionDesembolsoDTO;
 	}
 	
 	public SolicitudLegalizacionDesembolsoDTO getSolicitudLegalizacionDesembolsoDTO (){
 		return solicitudLegalizacionDesembolsoDTO;
 	}
  
}