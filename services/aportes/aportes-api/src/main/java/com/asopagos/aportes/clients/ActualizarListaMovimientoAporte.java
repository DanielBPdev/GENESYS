package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.MovimientoAporteModeloDTO;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/actualizarListaMovimientoAporte
 */
public class ActualizarListaMovimientoAporte extends ServiceClient { 
    	private List<MovimientoAporteModeloDTO> listaMovimientoAporteDTO;
  
  
 	public ActualizarListaMovimientoAporte (List<MovimientoAporteModeloDTO> listaMovimientoAporteDTO){
 		super();
		this.listaMovimientoAporteDTO=listaMovimientoAporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaMovimientoAporteDTO == null ? null : Entity.json(listaMovimientoAporteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaMovimientoAporteDTO (List<MovimientoAporteModeloDTO> listaMovimientoAporteDTO){
 		this.listaMovimientoAporteDTO=listaMovimientoAporteDTO;
 	}
 	
 	public List<MovimientoAporteModeloDTO> getListaMovimientoAporteDTO (){
 		return listaMovimientoAporteDTO;
 	}
  
}