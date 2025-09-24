package com.asopagos.tareashumanas.clients;

import com.asopagos.tareashumanas.dto.TareaDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/tareasHumanas/{idProceso}/last
 */
public class ObtenerUltimaTareaTerminada extends ServiceClient {
 
  	private Long idProceso;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private TareaDTO result;
  
 	public ObtenerUltimaTareaTerminada (Long idProceso){
 		super();
		this.idProceso=idProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idProceso", idProceso)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (TareaDTO) response.readEntity(TareaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public TareaDTO getResult() {
		return result;
	}

 	public void setIdProceso (Long idProceso){
 		this.idProceso=idProceso;
 	}
 	
 	public Long getIdProceso (){
 		return idProceso;
 	}
  
  
}