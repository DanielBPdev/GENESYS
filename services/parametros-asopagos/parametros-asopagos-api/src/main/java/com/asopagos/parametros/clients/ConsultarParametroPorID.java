package com.asopagos.parametros.clients;

import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/parametros/admon/{nombreClase}/{idEntidad}
 */
public class ConsultarParametroPorID extends ServiceClient {
 
  	private String idEntidad;
  	private String nombreClase;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Object result;
  
 	public ConsultarParametroPorID (String idEntidad,String nombreClase){
 		super();
		this.idEntidad=idEntidad;
		this.nombreClase=nombreClase;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEntidad", idEntidad)
						.resolveTemplate("nombreClase", nombreClase)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Object) response.readEntity(Object.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Object getResult() {
		return result;
	}

 	public void setIdEntidad (String idEntidad){
 		this.idEntidad=idEntidad;
 	}
 	
 	public String getIdEntidad (){
 		return idEntidad;
 	}
  	public void setNombreClase (String nombreClase){
 		this.nombreClase=nombreClase;
 	}
 	
 	public String getNombreClase (){
 		return nombreClase;
 	}
  
  
}