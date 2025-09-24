package com.asopagos.afiliaciones.personas.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/solicitudAfiliacionPersona
 */
public class CrearSolicitudAfiliacionPersonaSolo extends ServiceClient { 
    private Long  rolAfiliado;
	private Long solicitudGlobal;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
  
 	public CrearSolicitudAfiliacionPersonaSolo (Long rolAfiliado, Long solicitudGlobal){
 		super();
		this.rolAfiliado = rolAfiliado;
		this.solicitudGlobal = solicitudGlobal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("rolAfiliado", rolAfiliado)
			.queryParam("solicitudGlobal", solicitudGlobal)
			.request(MediaType.APPLICATION_JSON)
			.post(Entity.json(null));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	
  
}