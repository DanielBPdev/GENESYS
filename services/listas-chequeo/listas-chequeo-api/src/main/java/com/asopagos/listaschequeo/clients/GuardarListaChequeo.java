package com.asopagos.listaschequeo.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.ListaChequeoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/listasChequeo
 */
public class GuardarListaChequeo extends ServiceClient { 
    	private ListaChequeoDTO listaChequeo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public GuardarListaChequeo (ListaChequeoDTO listaChequeo){
 		super();
		this.listaChequeo=listaChequeo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaChequeo == null ? null : Entity.json(listaChequeo));
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

 
  
  	public void setListaChequeo (ListaChequeoDTO listaChequeo){
 		this.listaChequeo=listaChequeo;
 	}
 	
 	public ListaChequeoDTO getListaChequeo (){
 		return listaChequeo;
 	}
  
}