package com.asopagos.cartera.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.EmpleadorModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarEmpleadorCartera
 */
public class ConsultarEmpleadorCartera extends ServiceClient {
 
  
  	private Long idCartera;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EmpleadorModeloDTO result;
  
 	public ConsultarEmpleadorCartera (Long idCartera){
 		super();
		this.idCartera=idCartera;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCartera", idCartera)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EmpleadorModeloDTO) response.readEntity(EmpleadorModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EmpleadorModeloDTO getResult() {
		return result;
	}

 
  	public void setIdCartera (Long idCartera){
 		this.idCartera=idCartera;
 	}
 	
 	public Long getIdCartera (){
 		return idCartera;
 	}
  
}