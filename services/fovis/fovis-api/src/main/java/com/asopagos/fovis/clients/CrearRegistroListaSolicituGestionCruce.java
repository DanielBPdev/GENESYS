package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.SolicitudGestionCruceDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisCargue/crearRegistroListaSolicituGestionCruce
 */
public class CrearRegistroListaSolicituGestionCruce extends ServiceClient { 
    	private List<SolicitudGestionCruceDTO> listSolicitudGestionCruce;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudGestionCruceDTO> result;
  
 	public CrearRegistroListaSolicituGestionCruce (List<SolicitudGestionCruceDTO> listSolicitudGestionCruce){
 		super();
		this.listSolicitudGestionCruce=listSolicitudGestionCruce;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listSolicitudGestionCruce == null ? null : Entity.json(listSolicitudGestionCruce));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<SolicitudGestionCruceDTO>) response.readEntity(new GenericType<List<SolicitudGestionCruceDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<SolicitudGestionCruceDTO> getResult() {
		return result;
	}

 
  
  	public void setListSolicitudGestionCruce (List<SolicitudGestionCruceDTO> listSolicitudGestionCruce){
 		this.listSolicitudGestionCruce=listSolicitudGestionCruce;
 	}
 	
 	public List<SolicitudGestionCruceDTO> getListSolicitudGestionCruce (){
 		return listSolicitudGestionCruce;
 	}
  
}