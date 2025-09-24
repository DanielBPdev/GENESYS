package com.asopagos.cartera.clients;

import java.util.List;
import com.asopagos.dto.modelo.DetalleSolicitudGestionCobroModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/actualizarDetallesSolicitudGestionCobro
 */
public class ActualizarDetallesSolicitudGestionCobro extends ServiceClient { 
    	private List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleSolicitudGestionCobroModeloDTO;
  
  
 	public ActualizarDetallesSolicitudGestionCobro (List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleSolicitudGestionCobroModeloDTO){
 		super();
		this.listaDetalleSolicitudGestionCobroModeloDTO=listaDetalleSolicitudGestionCobroModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaDetalleSolicitudGestionCobroModeloDTO == null ? null : Entity.json(listaDetalleSolicitudGestionCobroModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaDetalleSolicitudGestionCobroModeloDTO (List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleSolicitudGestionCobroModeloDTO){
 		this.listaDetalleSolicitudGestionCobroModeloDTO=listaDetalleSolicitudGestionCobroModeloDTO;
 	}
 	
 	public List<DetalleSolicitudGestionCobroModeloDTO> getListaDetalleSolicitudGestionCobroModeloDTO (){
 		return listaDetalleSolicitudGestionCobroModeloDTO;
 	}
  
}