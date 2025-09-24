package com.asopagos.pila.clients;

import java.lang.Long;
import com.asopagos.dto.pila.RespuestaConsultaEmpleadorDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/consultarDatosEmpleadorByRegistroDetallado
 */
public class ConsultarDatosEmpleadorByRegistroDetallado extends ServiceClient {
 
  
  	private Long idRegDetallado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Object[] result;
  
 	public ConsultarDatosEmpleadorByRegistroDetallado(Long idRegDetallado){
 		super();
		this.idRegDetallado=idRegDetallado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idRegDetallado", idRegDetallado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Object[]) response.readEntity(Object[].class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Object[] getResult() {
		return result;
	}

 
  	public void setIdRegDetallado(Long idRegDetallado){
 		this.idRegDetallado=idRegDetallado;
 	}
 	
 	public Long getIdRegDetallado(){
 		return idRegDetallado;
 	}
  
}