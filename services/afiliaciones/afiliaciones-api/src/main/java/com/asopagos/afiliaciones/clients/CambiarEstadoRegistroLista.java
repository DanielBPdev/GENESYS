package com.asopagos.afiliaciones.clients;

import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliaciones/cambiarEstadoRegistroListaEspecial
 */
public class CambiarEstadoRegistroLista extends ServiceClient { 
    	private ListaEspecialRevision lista;
  
  
 	public CambiarEstadoRegistroLista (ListaEspecialRevision lista){
 		super();
		this.lista=lista;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(lista == null ? null : Entity.json(lista));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setLista (ListaEspecialRevision lista){
 		this.lista=lista;
 	}
 	
 	public ListaEspecialRevision getLista (){
 		return lista;
 	}
  
}