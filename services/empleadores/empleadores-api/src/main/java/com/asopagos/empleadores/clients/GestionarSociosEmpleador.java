package com.asopagos.empleadores.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.ccf.personas.SocioEmpleador;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/empleadores/{idEmpleador}/sociosEmpleador/gestionar
 */
public class GestionarSociosEmpleador extends ServiceClient { 
  	private Long idEmpleador;
    	private List<SocioEmpleador> socios;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public GestionarSociosEmpleador (Long idEmpleador,List<SocioEmpleador> socios){
 		super();
		this.idEmpleador=idEmpleador;
		this.socios=socios;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.put(socios == null ? null : Entity.json(socios));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Long> getResult() {
		return result;
	}

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  
  	public void setSocios (List<SocioEmpleador> socios){
 		this.socios=socios;
 	}
 	
 	public List<SocioEmpleador> getSocios (){
 		return socios;
 	}
  
}