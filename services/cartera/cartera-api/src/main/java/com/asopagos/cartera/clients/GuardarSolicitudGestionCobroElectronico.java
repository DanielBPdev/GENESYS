package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.SolicitudGestionCobroElectronicoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarSolicitudGestionCobroElectronico
 */
public class GuardarSolicitudGestionCobroElectronico extends ServiceClient { 
    	private SolicitudGestionCobroElectronicoModeloDTO solicitudDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudGestionCobroElectronicoModeloDTO result;
  
 	public GuardarSolicitudGestionCobroElectronico (SolicitudGestionCobroElectronicoModeloDTO solicitudDTO){
 		super();
		this.solicitudDTO=solicitudDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudDTO == null ? null : Entity.json(solicitudDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudGestionCobroElectronicoModeloDTO) response.readEntity(SolicitudGestionCobroElectronicoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudGestionCobroElectronicoModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudDTO (SolicitudGestionCobroElectronicoModeloDTO solicitudDTO){
 		this.solicitudDTO=solicitudDTO;
 	}
 	
 	public SolicitudGestionCobroElectronicoModeloDTO getSolicitudDTO (){
 		return solicitudDTO;
 	}
  
}