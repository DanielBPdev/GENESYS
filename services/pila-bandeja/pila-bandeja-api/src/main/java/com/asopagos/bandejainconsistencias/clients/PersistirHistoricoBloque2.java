package com.asopagos.bandejainconsistencias.clients;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/persistirHistoricoBloque2
 */
public class PersistirHistoricoBloque2 extends ServiceClient { 
	
	private Long indicePlanilla;
  
  
 	public PersistirHistoricoBloque2 (Long indicePlanilla){
 		super();
		this.indicePlanilla = indicePlanilla;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(indicePlanilla == null ? null : Entity.json(indicePlanilla));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

	public Long getIndicePlanilla() {
		return this.indicePlanilla;
	}

	public void setIndicePlanilla(Long indicePlanilla) {
		this.indicePlanilla = indicePlanilla;
	}
  
}