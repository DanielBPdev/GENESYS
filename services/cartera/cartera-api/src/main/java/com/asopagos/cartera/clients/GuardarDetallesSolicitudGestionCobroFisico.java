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
 * /rest/cartera/guardarDetallesSolicitudGestionCobroFisico
 */
public class GuardarDetallesSolicitudGestionCobroFisico extends ServiceClient { 
   	private Long idSolicitudGlobal;
   	private List<DetalleSolicitudGestionCobroModeloDTO> detallesSolicitudGestionCobroModeloDTO;
  
  
 	public GuardarDetallesSolicitudGestionCobroFisico (Long idSolicitudGlobal,List<DetalleSolicitudGestionCobroModeloDTO> detallesSolicitudGestionCobroModeloDTO){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
		this.detallesSolicitudGestionCobroModeloDTO=detallesSolicitudGestionCobroModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudGlobal", idSolicitudGlobal)
			.request(MediaType.APPLICATION_JSON)
			.post(detallesSolicitudGestionCobroModeloDTO == null ? null : Entity.json(detallesSolicitudGestionCobroModeloDTO));
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
  
  	public void setDetallesSolicitudGestionCobroModeloDTO (List<DetalleSolicitudGestionCobroModeloDTO> detallesSolicitudGestionCobroModeloDTO){
 		this.detallesSolicitudGestionCobroModeloDTO=detallesSolicitudGestionCobroModeloDTO;
 	}
 	
 	public List<DetalleSolicitudGestionCobroModeloDTO> getDetallesSolicitudGestionCobroModeloDTO (){
 		return detallesSolicitudGestionCobroModeloDTO;
 	}
  
}