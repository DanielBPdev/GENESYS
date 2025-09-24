package com.asopagos.empleadores.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/{idEmpresa}/crearActualizarRepresentanteLegal
 */
public class CrearActualizarRepresentanteLegal extends ServiceClient { 
  	private Long idEmpresa;
   	private Boolean titular;
   	private PersonaModeloDTO representante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearActualizarRepresentanteLegal (Long idEmpresa,Boolean titular,PersonaModeloDTO representante){
 		super();
		this.idEmpresa=idEmpresa;
		this.titular=titular;
		this.representante=representante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpresa", idEmpresa)
			.queryParam("titular", titular)
			.request(MediaType.APPLICATION_JSON)
			.post(representante == null ? null : Entity.json(representante));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
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
  
  	public void setRepresentante (PersonaModeloDTO representante){
 		this.representante=representante;
 	}
 	
 	public PersonaModeloDTO getRepresentante (){
 		return representante;
 	}
  
}