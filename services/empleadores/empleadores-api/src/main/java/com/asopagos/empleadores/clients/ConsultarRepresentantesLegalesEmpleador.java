package com.asopagos.empleadores.clients;

import java.lang.Long;
import java.lang.Boolean;
import com.asopagos.entidades.ccf.personas.Persona;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empleadores/{idEmpleador}/representantesLegales
 */
public class ConsultarRepresentantesLegalesEmpleador extends ServiceClient {
 
  	private Long idEmpleador;
  
  	private Boolean titular;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Persona result;
  
 	public ConsultarRepresentantesLegalesEmpleador (Long idEmpleador,Boolean titular){
 		super();
		this.idEmpleador=idEmpleador;
		this.titular=titular;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEmpleador", idEmpleador)
									.queryParam("titular", titular)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Persona) response.readEntity(Persona.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Persona getResult() {
		return result;
	}

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  	public void setTitular (Boolean titular){
 		this.titular=titular;
 	}
 	
 	public Boolean getTitular (){
 		return titular;
 	}
  
}