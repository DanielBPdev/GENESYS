package com.asopagos.empresas.clients;

import java.lang.Long;
import java.lang.Boolean;
import com.asopagos.entidades.ccf.personas.Persona;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empresas/representantesLegales
 */
public class ConsultarRepresentantesLegalesEmpresa extends ServiceClient {
 
  
  	private Long idEmpresa;
  	private Boolean titular;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Persona result;
  
 	public ConsultarRepresentantesLegalesEmpresa (Long idEmpresa,Boolean titular){
 		super();
		this.idEmpresa=idEmpresa;
		this.titular=titular;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idEmpresa", idEmpresa)
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

 
  	public void setIdEmpresa (Long idEmpresa){
 		this.idEmpresa=idEmpresa;
 	}
 	
 	public Long getIdEmpresa (){
 		return idEmpresa;
 	}
  	public void setTitular (Boolean titular){
 		this.titular=titular;
 	}
 	
 	public Boolean getTitular (){
 		return titular;
 	}
  
}