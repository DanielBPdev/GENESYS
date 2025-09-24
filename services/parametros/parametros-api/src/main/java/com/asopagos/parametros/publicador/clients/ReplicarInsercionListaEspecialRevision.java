package com.asopagos.parametros.publicador.clients;

import com.asopagos.entidades.transversal.personas.ListaEspecialRevision;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/parametrosSubsidioCaja/replicarInsercionListaEspecialRevision
 */
public class ReplicarInsercionListaEspecialRevision extends ServiceClient { 
    	private ListaEspecialRevision listaEspecialRevision;
  
  
 	public ReplicarInsercionListaEspecialRevision (ListaEspecialRevision listaEspecialRevision){
 		super();
		this.listaEspecialRevision=listaEspecialRevision;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaEspecialRevision == null ? null : Entity.json(listaEspecialRevision));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaEspecialRevision (ListaEspecialRevision listaEspecialRevision){
 		this.listaEspecialRevision=listaEspecialRevision;
 	}
 	
 	public ListaEspecialRevision getListaEspecialRevision (){
 		return listaEspecialRevision;
 	}
  
}