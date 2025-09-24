package com.asopagos.entidaddescuento.clients;

import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/entidadDescuento/archivoDescuentos/ejecutarActualizacionArchivosDescuento
 */
public class EjecutarActualizacionArchivosDescuento extends ServiceClient {
 
  
  	private Long codigoEntidad;
  	private Long idArchivo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public EjecutarActualizacionArchivosDescuento (Long idArchivo,Long codigoEntidad){
 		super();
		this.codigoEntidad=codigoEntidad;
		this.idArchivo=idArchivo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("codigoEntidad", codigoEntidad)
						.queryParam("idArchivo", idArchivo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Long getResult() {
		return result;
	}

 
  	public void setCodigoEntidad (Long codigoEntidad){
 		this.codigoEntidad=codigoEntidad;
 	}
 	
 	public Long getCodigoEntidad (){
 		return codigoEntidad;
 	}
  	public void setIdArchivo (Long idArchivo){
 		this.idArchivo=idArchivo;
 	}
 	
 	public Long getIdArchivo (){
 		return idArchivo;
 	}
  
}