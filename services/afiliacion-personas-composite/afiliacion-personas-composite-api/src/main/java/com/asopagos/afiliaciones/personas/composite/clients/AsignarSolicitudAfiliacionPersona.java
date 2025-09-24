package com.asopagos.afiliaciones.personas.composite.clients;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.afiliaciones.personas.composite.dto.AsignarSolicitudAfiliacionPersonaDTO;

import com.asopagos.services.common.ServiceClient;

public class AsignarSolicitudAfiliacionPersona extends ServiceClient{

	private AsignarSolicitudAfiliacionPersonaDTO solicitud;

 	public AsignarSolicitudAfiliacionPersona (AsignarSolicitudAfiliacionPersonaDTO solicitud){
 		super();
        this.solicitud = solicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitud == null ? null : Entity.json(solicitud));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {}
}
