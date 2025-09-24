package com.asopagos.aportes.clients;

import java.lang.Long;
import java.util.Map;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/validarAporteProcesado/{idAporteGeneral}/{idAporteDetallado}
 */
public class ValidarAporteProcesado extends ServiceClient {
 
  	private Long idAporteGeneral;
  	private Long idAporteDetallado;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Boolean> result;
  
 	public ValidarAporteProcesado (Long idAporteGeneral,Long idAporteDetallado){
 		super();
		this.idAporteGeneral=idAporteGeneral;
		this.idAporteDetallado=idAporteDetallado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idAporteGeneral", idAporteGeneral)
						.resolveTemplate("idAporteDetallado", idAporteDetallado)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Map<String,Boolean>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<String,Boolean> getResult() {
		return result;
	}

 	public void setIdAporteGeneral (Long idAporteGeneral){
 		this.idAporteGeneral=idAporteGeneral;
 	}
 	
 	public Long getIdAporteGeneral (){
 		return idAporteGeneral;
 	}
  	public void setIdAporteDetallado (Long idAporteDetallado){
 		this.idAporteDetallado=idAporteDetallado;
 	}
 	
 	public Long getIdAporteDetallado (){
 		return idAporteDetallado;
 	}
  
  
}