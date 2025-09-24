package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/registrarEscalamientoMultiple
 */
public class RegistrarEscalamientoMultipleFront extends ServiceClient { 
    	private SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudPostulacionFOVISDTO result;
  
 	public RegistrarEscalamientoMultipleFront (SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO){
 		super();
		this.solicitudPostulacionFOVISDTO=solicitudPostulacionFOVISDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudPostulacionFOVISDTO == null ? null : Entity.json(solicitudPostulacionFOVISDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudPostulacionFOVISDTO) response.readEntity(SolicitudPostulacionFOVISDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudPostulacionFOVISDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudPostulacionFOVISDTO (SolicitudPostulacionFOVISDTO solicitudPostulacionFOVISDTO){
 		this.solicitudPostulacionFOVISDTO=solicitudPostulacionFOVISDTO;
 	}
 	
 	public SolicitudPostulacionFOVISDTO getSolicitudPostulacionFOVISDTO (){
 		return solicitudPostulacionFOVISDTO;
 	}
  
}