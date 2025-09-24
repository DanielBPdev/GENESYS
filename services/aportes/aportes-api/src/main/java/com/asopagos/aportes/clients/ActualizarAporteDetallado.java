package com.asopagos.aportes.clients;

import java.util.List;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/aportes/actualizarAporteDetallado
 */
public class ActualizarAporteDetallado extends ServiceClient { 
    	private List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO;
  
  
 	public ActualizarAporteDetallado (List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO){
 		super();
		this.listaAporteDetalladoDTO=listaAporteDetalladoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(listaAporteDetalladoDTO == null ? null : Entity.json(listaAporteDetalladoDTO));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaAporteDetalladoDTO (List<AporteDetalladoModeloDTO> listaAporteDetalladoDTO){
 		this.listaAporteDetalladoDTO=listaAporteDetalladoDTO;
 	}
 	
 	public List<AporteDetalladoModeloDTO> getListaAporteDetalladoDTO (){
 		return listaAporteDetalladoDTO;
 	}
  
}