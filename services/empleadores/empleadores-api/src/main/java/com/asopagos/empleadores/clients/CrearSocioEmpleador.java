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
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/{idEmpleador}/sociosEmpleador
 */
public class CrearSocioEmpleador extends ServiceClient { 
  	private Long idEmpleador;
    	private List<SocioEmpleador> listSocios;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public CrearSocioEmpleador (Long idEmpleador,List<SocioEmpleador> listSocios){
 		super();
		this.idEmpleador=idEmpleador;
		this.listSocios=listSocios;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.post(listSocios == null ? null : Entity.json(listSocios));
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
  
  
  	public void setListSocios (List<SocioEmpleador> listSocios){
 		this.listSocios=listSocios;
 	}
 	
 	public List<SocioEmpleador> getListSocios (){
 		return listSocios;
 	}
  
}