package com.asopagos.subsidiomonetario.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultar/identificador/conjuntoValidacion
 */
public class ConsultarIdentificadorConjuntoValidacion extends ServiceClient {
 
  
  	private ConjuntoValidacionSubsidioEnum validacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public ConsultarIdentificadorConjuntoValidacion (ConjuntoValidacionSubsidioEnum validacion){
 		super();
		this.validacion=validacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("validacion", validacion)
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

 
  	public void setValidacion (ConjuntoValidacionSubsidioEnum validacion){
 		this.validacion=validacion;
 	}
 	
 	public ConjuntoValidacionSubsidioEnum getValidacion (){
 		return validacion;
 	}
  
}