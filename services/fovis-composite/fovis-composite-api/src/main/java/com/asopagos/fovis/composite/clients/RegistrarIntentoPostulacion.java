package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/registrarIntentoPostulacion
 */
public class RegistrarIntentoPostulacion extends ServiceClient { 
    	private SolicitudPostulacionFOVISDTO solicitudPostulacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarIntentoPostulacion (SolicitudPostulacionFOVISDTO solicitudPostulacionDTO){
 		super();
		this.solicitudPostulacionDTO=solicitudPostulacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudPostulacionDTO == null ? null : Entity.json(solicitudPostulacionDTO));
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

 
  
  	public void setSolicitudPostulacionDTO (SolicitudPostulacionFOVISDTO solicitudPostulacionDTO){
 		this.solicitudPostulacionDTO=solicitudPostulacionDTO;
 	}
 	
 	public SolicitudPostulacionFOVISDTO getSolicitudPostulacionDTO (){
 		return solicitudPostulacionDTO;
 	}
  
}