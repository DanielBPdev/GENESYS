package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import com.asopagos.subsidiomonetario.dto.RespuestaGenericaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/seleccionarEmpleador/consultarEstado/{empleadorId}
 */
public class SeleccionarEmpleador extends ServiceClient {
 
  	private Long empleadorId;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaGenericaDTO result;
  
 	public SeleccionarEmpleador (Long empleadorId){
 		super();
		this.empleadorId=empleadorId;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("empleadorId", empleadorId)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RespuestaGenericaDTO) response.readEntity(RespuestaGenericaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RespuestaGenericaDTO getResult() {
		return result;
	}

 	public void setEmpleadorId (Long empleadorId){
 		this.empleadorId=empleadorId;
 	}
 	
 	public Long getEmpleadorId (){
 		return empleadorId;
 	}
  
  
}