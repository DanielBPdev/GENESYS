package com.asopagos.pila.composite.clients;

import java.lang.Long;
import java.util.Map;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PilaComposite/verificarEstadoRegistrosAdicionRegistrarRelacionarAporte
 */
public class VerificarEstadoRegistrosAdicionRegistrarRelacionarAporte extends ServiceClient { 
   	private Long idIndicePlanilla;
  	private Long idRegistroGeneral;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public VerificarEstadoRegistrosAdicionRegistrarRelacionarAporte (Long idIndicePlanilla,Long idRegistroGeneral){
 		super();
		this.idIndicePlanilla=idIndicePlanilla;
		this.idRegistroGeneral=idRegistroGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idIndicePlanilla", idIndicePlanilla)
			.queryParam("idRegistroGeneral", idRegistroGeneral)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
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

 
  	public void setIdIndicePlanilla (Long idIndicePlanilla){
 		this.idIndicePlanilla=idIndicePlanilla;
 	}
 	
 	public Long getIdIndicePlanilla (){
 		return idIndicePlanilla;
 	}
  	public void setIdRegistroGeneral (Long idRegistroGeneral){
 		this.idRegistroGeneral=idRegistroGeneral;
 	}
 	
 	public Long getIdRegistroGeneral (){
 		return idRegistroGeneral;
 	}
  
  
}