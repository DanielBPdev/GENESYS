package com.asopagos.subsidiomonetario.composite.clients;

import java.util.Map;
import java.lang.String;
import com.asopagos.subsidiomonetario.dto.IniciarSolicitudLiquidacionMasivaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetarioComposite/cancelarLiquidacionMasivaComposite
 */
public class CancelarLiquidacionMasivaComposite extends ServiceClient { 
    	private IniciarSolicitudLiquidacionMasivaDTO params;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public CancelarLiquidacionMasivaComposite (IniciarSolicitudLiquidacionMasivaDTO params){
 		super();
		this.params=params;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(params == null ? null : Entity.json(params));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,String> getResult() {
		return result;
	}

 
  
  	public void setParams (IniciarSolicitudLiquidacionMasivaDTO params){
 		this.params=params;
 	}
 	
 	public IniciarSolicitudLiquidacionMasivaDTO getParams (){
 		return params;
 	}
  
}