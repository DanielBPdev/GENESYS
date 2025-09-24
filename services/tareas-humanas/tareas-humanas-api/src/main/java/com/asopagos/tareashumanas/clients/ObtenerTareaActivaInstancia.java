package com.asopagos.tareashumanas.clients;

import com.asopagos.tareashumanas.dto.TareaDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/tareasHumanas/obtenerTareaActivaInstancia
 */
public class ObtenerTareaActivaInstancia extends ServiceClient {
 
  
  	private Long idInstanciaProceso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private TareaDTO result;
  
 	public ObtenerTareaActivaInstancia (Long idInstanciaProceso){
 		super();
		this.idInstanciaProceso=idInstanciaProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idInstanciaProceso", idInstanciaProceso)
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

 
  	public void setIdInstanciaProceso (Long idInstanciaProceso){
 		this.idInstanciaProceso=idInstanciaProceso;
 	}
 	
 	public Long getIdInstanciaProceso (){
 		return idInstanciaProceso;
 	}
  
}