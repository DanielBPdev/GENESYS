package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.SolicitudPreventivaAgrupadoraModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarSolicitudPreventivaAgrupadora
 */
public class GuardarSolicitudPreventivaAgrupadora extends ServiceClient { 
    	private SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudPreventivaAgrupadoraModeloDTO result;
  
 	public GuardarSolicitudPreventivaAgrupadora (SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO){
 		super();
		this.solicitudPreventivaAgrupadoraModeloDTO=solicitudPreventivaAgrupadoraModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudPreventivaAgrupadoraModeloDTO == null ? null : Entity.json(solicitudPreventivaAgrupadoraModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudPreventivaAgrupadoraModeloDTO) response.readEntity(SolicitudPreventivaAgrupadoraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudPreventivaAgrupadoraModeloDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudPreventivaAgrupadoraModeloDTO (SolicitudPreventivaAgrupadoraModeloDTO solicitudPreventivaAgrupadoraModeloDTO){
 		this.solicitudPreventivaAgrupadoraModeloDTO=solicitudPreventivaAgrupadoraModeloDTO;
 	}
 	
 	public SolicitudPreventivaAgrupadoraModeloDTO getSolicitudPreventivaAgrupadoraModeloDTO (){
 		return solicitudPreventivaAgrupadoraModeloDTO;
 	}
  
}