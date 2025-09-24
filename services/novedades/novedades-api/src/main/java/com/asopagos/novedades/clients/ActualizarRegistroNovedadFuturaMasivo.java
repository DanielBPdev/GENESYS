package com.asopagos.novedades.clients;

import java.util.List;
import com.asopagos.dto.modelo.RegistroNovedadFuturaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/actualizarRegistroNovedadFuturaMasivo
 */
public class ActualizarRegistroNovedadFuturaMasivo extends ServiceClient { 
    	private List<RegistroNovedadFuturaModeloDTO> listRegistros;
  
  
 	public ActualizarRegistroNovedadFuturaMasivo (List<RegistroNovedadFuturaModeloDTO> listRegistros){
 		super();
		this.listRegistros=listRegistros;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listRegistros == null ? null : Entity.json(listRegistros));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListRegistros (List<RegistroNovedadFuturaModeloDTO> listRegistros){
 		this.listRegistros=listRegistros;
 	}
 	
 	public List<RegistroNovedadFuturaModeloDTO> getListRegistros (){
 		return listRegistros;
 	}
  
}