package com.asopagos.entidades.pagadoras.clients;

import javax.ws.rs.core.Response;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/entidadesPagadoras/{idEntidadPagadora}/solicitudesAsociacionPersonas/generarArchivo/{consecutivoGestion}
 */
public class GenerarArchivoGestionSolicitudesAsociacion extends ServiceClient {
 
  	private Long idEntidadPagadora;
  	private String consecutivoGestion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Response result;
  
 	public GenerarArchivoGestionSolicitudesAsociacion (Long idEntidadPagadora,String consecutivoGestion){
 		super();
		this.idEntidadPagadora=idEntidadPagadora;
		this.consecutivoGestion=consecutivoGestion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEntidadPagadora", idEntidadPagadora)
						.resolveTemplate("consecutivoGestion", consecutivoGestion)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Response) response.readEntity(Response.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Response getResult() {
		return result;
	}

 	public void setIdEntidadPagadora (Long idEntidadPagadora){
 		this.idEntidadPagadora=idEntidadPagadora;
 	}
 	
 	public Long getIdEntidadPagadora (){
 		return idEntidadPagadora;
 	}
  	public void setConsecutivoGestion (String consecutivoGestion){
 		this.consecutivoGestion=consecutivoGestion;
 	}
 	
 	public String getConsecutivoGestion (){
 		return consecutivoGestion;
 	}
  
  
}