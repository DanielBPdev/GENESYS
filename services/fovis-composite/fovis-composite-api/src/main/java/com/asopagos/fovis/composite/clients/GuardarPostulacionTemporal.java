package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/guardarPostulacionTemporal
 */
public class GuardarPostulacionTemporal extends ServiceClient { 
    	private SolicitudPostulacionFOVISDTO solicitudPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudPostulacionFOVISDTO result;
  
 	public GuardarPostulacionTemporal (SolicitudPostulacionFOVISDTO solicitudPostulacion){
 		super();
		this.solicitudPostulacion=solicitudPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudPostulacion == null ? null : Entity.json(solicitudPostulacion));
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

 
  
  	public void setSolicitudPostulacion (SolicitudPostulacionFOVISDTO solicitudPostulacion){
 		this.solicitudPostulacion=solicitudPostulacion;
 	}
 	
 	public SolicitudPostulacionFOVISDTO getSolicitudPostulacion (){
 		return solicitudPostulacion;
 	}
  
}