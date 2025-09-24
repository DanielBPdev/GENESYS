package com.asopagos.pila.clients;

import java.util.List;
import com.asopagos.dto.ArchivoPilaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/procesarSeleccionOI
 */
public class ProcesarSeleccionOI extends ServiceClient { 
    	private List<ArchivoPilaDTO> listaArchivoPila;
  
  
 	public ProcesarSeleccionOI (List<ArchivoPilaDTO> listaArchivoPila){
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
	}
	

 
  
  	public void setListaArchivoPila (List<ArchivoPilaDTO> listaArchivoPila){
 		this.listaArchivoPila=listaArchivoPila;
 	}
 	
 	public List<ArchivoPilaDTO> getListaArchivoPila (){
 		return listaArchivoPila;
 	}
  
}