package com.asopagos.solicitudes.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudes/datosTemporales
 */
public class ConsultarDatosTemporales extends ServiceClient {
 
  
  	private Long idSolicitudGlobal;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ConsultarDatosTemporales (Long idSolicitudGlobal){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitudGlobal", idSolicitudGlobal)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public String getResult() {
		return result;
	}

 
  	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
}