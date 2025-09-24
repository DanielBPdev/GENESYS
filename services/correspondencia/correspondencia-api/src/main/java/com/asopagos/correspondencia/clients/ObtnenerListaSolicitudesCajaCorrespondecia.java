package com.asopagos.correspondencia.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest//cajasCorrespondencia/{codigoEtiqueta}/solicitudes
 */
public class ObtnenerListaSolicitudesCajaCorrespondecia extends ServiceClient {
 
  	private String codigoEtiqueta;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<String> result;
  
 	public ObtnenerListaSolicitudesCajaCorrespondecia (String codigoEtiqueta){
 		super();
		this.codigoEtiqueta=codigoEtiqueta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("codigoEtiqueta", codigoEtiqueta)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<String>) response.readEntity(new GenericType<List<String>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<String> getResult() {
		return result;
	}

 	public void setCodigoEtiqueta (String codigoEtiqueta){
 		this.codigoEtiqueta=codigoEtiqueta;
 	}
 	
 	public String getCodigoEtiqueta (){
 		return codigoEtiqueta;
 	}
  
  
}