package com.asopagos.aportes.clients;

import java.lang.Short;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/buscarMunicipio/{codigoMunicipio}
 */
public class BuscarMunicipio extends ServiceClient {
 
  	private String codigoMunicipio;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Short result;
  
 	public BuscarMunicipio (String codigoMunicipio){
 		super();
		this.codigoMunicipio=codigoMunicipio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("codigoMunicipio", codigoMunicipio)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Short) response.readEntity(Short.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Short getResult() {
		return result;
	}

 	public void setCodigoMunicipio (String codigoMunicipio){
 		this.codigoMunicipio=codigoMunicipio;
 	}
 	
 	public String getCodigoMunicipio (){
 		return codigoMunicipio;
 	}
  
  
}