package com.asopagos.tareashumanas.clients;

import java.lang.Long;
import java.util.Map;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.rest.security.dto.UserDTO;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/tareasHumanas/obtenerTareaActivaSat
 */
public class ObtenerTareaActivaSat extends ServiceClient {
 
  
  	private Long idInstanciaProceso;
    private String nombreUsuario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public ObtenerTareaActivaSat (Long idInstanciaProceso,String nombreUsuario) {
 		super();
		this.idInstanciaProceso=idInstanciaProceso;
        this.nombreUsuario = nombreUsuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idInstanciaProceso", idInstanciaProceso)
                                    .queryParam("nombreUsuario", nombreUsuario)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Map<String,Object>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<String,Object> getResult() {
		return result;
	}

 
  	public void setIdInstanciaProceso (Long idInstanciaProceso){
 		this.idInstanciaProceso=idInstanciaProceso;
 	}
 	
 	public Long getIdInstanciaProceso (){
 		return idInstanciaProceso;
 	}
  
}