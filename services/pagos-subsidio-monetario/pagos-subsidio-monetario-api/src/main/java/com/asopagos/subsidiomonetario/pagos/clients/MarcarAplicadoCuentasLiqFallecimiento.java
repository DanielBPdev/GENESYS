package com.asopagos.subsidiomonetario.pagos.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/marcarAplicadoCuentasLiqFallecimiento
 */
public class MarcarAplicadoCuentasLiqFallecimiento extends ServiceClient { 
    	private List<Long> listaIdsAdminSubsidio;
  
  
 	public MarcarAplicadoCuentasLiqFallecimiento (List<Long> listaIdsAdminSubsidio){
 		super();
		this.listaIdsAdminSubsidio=listaIdsAdminSubsidio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaIdsAdminSubsidio == null ? null : Entity.json(listaIdsAdminSubsidio));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaIdsAdminSubsidio (List<Long> listaIdsAdminSubsidio){
 		this.listaIdsAdminSubsidio=listaIdsAdminSubsidio;
 	}
 	
 	public List<Long> getListaIdsAdminSubsidio (){
 		return listaIdsAdminSubsidio;
 	}
  
}