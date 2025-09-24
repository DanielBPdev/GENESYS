package com.asopagos.afiliaciones.empleadores.composite.clients;

import com.asopagos.afiliaciones.dto.ListaEspecialRevisionDTO;
import java.lang.Integer;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/registarEmpleadorListaEspecial
 */
public class RegistrarEmpleadorEnListaEspecialRevision extends ServiceClient { 
    	private ListaEspecialRevisionDTO listaEspecialRevisionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Integer result;
  
 	public RegistrarEmpleadorEnListaEspecialRevision (ListaEspecialRevisionDTO listaEspecialRevisionDTO){
 		super();
		this.listaEspecialRevisionDTO=listaEspecialRevisionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaEspecialRevisionDTO == null ? null : Entity.json(listaEspecialRevisionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Integer) response.readEntity(Integer.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Integer getResult() {
		return result;
	}

 
  
  	public void setListaEspecialRevisionDTO (ListaEspecialRevisionDTO listaEspecialRevisionDTO){
 		this.listaEspecialRevisionDTO=listaEspecialRevisionDTO;
 	}
 	
 	public ListaEspecialRevisionDTO getListaEspecialRevisionDTO (){
 		return listaEspecialRevisionDTO;
 	}
  
}