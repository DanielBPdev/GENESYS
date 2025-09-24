package com.asopagos.cartera.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.DetalleSolicitudGestionCobroModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarListaDetalleSolicitudGestionCobroFisico
 */
public class GuardarListaDetalleSolicitudGestionCobroFisico extends ServiceClient { 
   	private Long idSolicitudGlobal;
   	private List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleSolicitudGestionCobroModeloDTO;
  
  
 	public GuardarListaDetalleSolicitudGestionCobroFisico (Long idSolicitudGlobal,List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleSolicitudGestionCobroModeloDTO){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
		this.listaDetalleSolicitudGestionCobroModeloDTO=listaDetalleSolicitudGestionCobroModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudGlobal", idSolicitudGlobal)
			.request(MediaType.APPLICATION_JSON)
			.post(listaDetalleSolicitudGestionCobroModeloDTO == null ? null : Entity.json(listaDetalleSolicitudGestionCobroModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  	public void setListaDetalleSolicitudGestionCobroModeloDTO (List<DetalleSolicitudGestionCobroModeloDTO> listaDetalleSolicitudGestionCobroModeloDTO){
 		this.listaDetalleSolicitudGestionCobroModeloDTO=listaDetalleSolicitudGestionCobroModeloDTO;
 	}
 	
 	public List<DetalleSolicitudGestionCobroModeloDTO> getListaDetalleSolicitudGestionCobroModeloDTO (){
 		return listaDetalleSolicitudGestionCobroModeloDTO;
 	}
  
}