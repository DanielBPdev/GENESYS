package com.asopagos.afiliaciones.clients;

import com.asopagos.afiliaciones.dto.ActualizacionEstadoListaEspecialDTO;
import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliaciones/consultarRegistroListaEspecialRevision
 */
public class ConsultarRegistroListaEspecialRevision extends ServiceClient { 
    	private ActualizacionEstadoListaEspecialDTO actualizacionEstadoListaEspecialDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ListaEspecialRevision result;
  
 	public ConsultarRegistroListaEspecialRevision (ActualizacionEstadoListaEspecialDTO actualizacionEstadoListaEspecialDTO){
 		super();
		this.actualizacionEstadoListaEspecialDTO=actualizacionEstadoListaEspecialDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(actualizacionEstadoListaEspecialDTO == null ? null : Entity.json(actualizacionEstadoListaEspecialDTO));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
		result = (ListaEspecialRevision) response.readEntity(ListaEspecialRevision.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ListaEspecialRevision getResult() {
		return result;
	}

 
  
  	public void setActualizacionEstadoListaEspecialDTO (ActualizacionEstadoListaEspecialDTO actualizacionEstadoListaEspecialDTO){
 		this.actualizacionEstadoListaEspecialDTO=actualizacionEstadoListaEspecialDTO;
 	}
 	
 	public ActualizacionEstadoListaEspecialDTO getActualizacionEstadoListaEspecialDTO (){
 		return actualizacionEstadoListaEspecialDTO;
 	}
  
}