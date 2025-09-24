package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/cargarArchivosPilaManual
 */
public class ReprocesarB0F extends ServiceClient { 
    	private List<Long> listaArchivoPila;
  
  	
  
 	public ReprocesarB0F (List<Long> listaArchivoPila){
 		super();
		this.listaArchivoPila=listaArchivoPila;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaArchivoPila == null ? null : Entity.json(listaArchivoPila));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		//result = (List<RespuestaServicioDTO>) response.readEntity(new GenericType<List<RespuestaServicioDTO>>(){});
	}
	
	

 
  
  	public void setListaArchivoPila (List<Long> listaArchivoPila){
 		this.listaArchivoPila=listaArchivoPila;
 	}
 	
 	public List<Long> getListaArchivoPila (){
 		return listaArchivoPila;
 	}
  
}