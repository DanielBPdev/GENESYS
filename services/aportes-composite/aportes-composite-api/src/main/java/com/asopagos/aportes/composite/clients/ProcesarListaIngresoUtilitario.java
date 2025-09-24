package com.asopagos.aportes.composite.clients;

import java.util.List;
import com.asopagos.aportes.composite.dto.ProcesoIngresoUtilitarioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/AportesComposite/procesarListaIngresoUtilitario
 */
public class ProcesarListaIngresoUtilitario extends ServiceClient { 
    	private List<ProcesoIngresoUtilitarioDTO> listaPersonasIngresar;
  
  
 	public ProcesarListaIngresoUtilitario (List<ProcesoIngresoUtilitarioDTO> listaPersonasIngresar){
 		super();
		this.listaPersonasIngresar=listaPersonasIngresar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaPersonasIngresar == null ? null : Entity.json(listaPersonasIngresar));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaPersonasIngresar (List<ProcesoIngresoUtilitarioDTO> listaPersonasIngresar){
 		this.listaPersonasIngresar=listaPersonasIngresar;
 	}
 	
 	public List<ProcesoIngresoUtilitarioDTO> getListaPersonasIngresar (){
 		return listaPersonasIngresar;
 	}
  
}