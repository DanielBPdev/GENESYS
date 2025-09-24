package com.asopagos.aportes.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import java.util.Map;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/procesarPaqueteAportesGenerales
 */
public class ProcesarPaqueteAportesGenerales extends ServiceClient { 
    	private Map<String,AporteGeneralModeloDTO> aportesGenerales;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Long> result;
  
 	public ProcesarPaqueteAportesGenerales (Map<String,AporteGeneralModeloDTO> aportesGenerales){
 		super();
		this.aportesGenerales=aportesGenerales;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(aportesGenerales == null ? null : Entity.json(aportesGenerales));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,Long>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,Long> getResult() {
		return result;
	}

 
  
  	public void setAportesGenerales (Map<String,AporteGeneralModeloDTO> aportesGenerales){
 		this.aportesGenerales=aportesGenerales;
 	}
 	
 	public Map<String,AporteGeneralModeloDTO> getAportesGenerales (){
 		return aportesGenerales;
 	}
  
}