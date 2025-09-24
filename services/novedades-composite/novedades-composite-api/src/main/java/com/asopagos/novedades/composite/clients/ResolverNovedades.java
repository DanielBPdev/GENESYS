package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.composite.dto.ResolverNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/consultarAnalisisNovedad
 */
public class ResolverNovedades extends ServiceClient { 
    private ResolverNovedadDTO resolverNovedadDTO;
  
 	public ResolverNovedades(ResolverNovedadDTO resolverNovedadDTO) {
 		super();
		this.resolverNovedadDTO = resolverNovedadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(Entity.json(resolverNovedadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

	public ResolverNovedadDTO getResolverNovedadDTO() {
		return this.resolverNovedadDTO;
	}

	public void setResolverNovedadDTO(ResolverNovedadDTO resolverNovedadDTO) {
		this.resolverNovedadDTO = resolverNovedadDTO;
	}

}