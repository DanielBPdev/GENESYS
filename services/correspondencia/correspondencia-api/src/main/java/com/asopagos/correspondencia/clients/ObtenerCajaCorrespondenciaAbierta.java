package com.asopagos.correspondencia.clients;

import com.asopagos.entidades.ccf.core.CajaCorrespondencia;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest//cajasCorrespondencia/abierta
 */
public class ObtenerCajaCorrespondenciaAbierta extends ServiceClient {
 
  
  	private String codigoSede;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CajaCorrespondencia result;
  
 	public ObtenerCajaCorrespondenciaAbierta (String codigoSede){
 		super();
		this.codigoSede=codigoSede;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("codigoSede", codigoSede)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CajaCorrespondencia) response.readEntity(CajaCorrespondencia.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CajaCorrespondencia getResult() {
		return result;
	}

 
  	public void setCodigoSede (String codigoSede){
 		this.codigoSede=codigoSede;
 	}
 	
 	public String getCodigoSede (){
 		return codigoSede;
 	}
  
}