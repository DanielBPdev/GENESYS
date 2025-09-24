package com.asopagos.constantes.parametros.clients;

import com.asopagos.entidades.ccf.general.Parametro;
import java.lang.Boolean;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/constantesparametros/consultarMargeToleranciaMoraAporte
 */
public class ConsultarMargeToleranciaMoraAporte extends ServiceClient {
 
  
  	private Boolean isPila;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Parametro result;
  
 	public ConsultarMargeToleranciaMoraAporte (Boolean isPila){
 		super();
		this.isPila=isPila;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("isPila", isPila)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Parametro) response.readEntity(Parametro.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Parametro getResult() {
		return result;
	}

 
  	public void setIsPila (Boolean isPila){
 		this.isPila=isPila;
 	}
 	
 	public Boolean getIsPila (){
 		return isPila;
 	}
  
}