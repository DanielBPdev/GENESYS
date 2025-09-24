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
 * /rest/cartera/guardarDetallesSolicitudGestionCobro
 */
public class GuardarDetallesSolicitudGestionCobro extends ServiceClient { 
   	private Long idSolicitudGlobal;
   	private List<DetalleSolicitudGestionCobroModeloDTO> detalleSolicitudGestionCobroModeloDTO;
  
  
 	public GuardarDetallesSolicitudGestionCobro (Long idSolicitudGlobal,List<DetalleSolicitudGestionCobroModeloDTO> detalleSolicitudGestionCobroModeloDTO){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
		this.detalleSolicitudGestionCobroModeloDTO=detalleSolicitudGestionCobroModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudGlobal", idSolicitudGlobal)
			.request(MediaType.APPLICATION_JSON)
			.post(detalleSolicitudGestionCobroModeloDTO == null ? null : Entity.json(detalleSolicitudGestionCobroModeloDTO));
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
  
  	public void setDetalleSolicitudGestionCobroModeloDTO (List<DetalleSolicitudGestionCobroModeloDTO> detalleSolicitudGestionCobroModeloDTO){
 		this.detalleSolicitudGestionCobroModeloDTO=detalleSolicitudGestionCobroModeloDTO;
 	}
 	
 	public List<DetalleSolicitudGestionCobroModeloDTO> getDetalleSolicitudGestionCobroModeloDTO (){
 		return detalleSolicitudGestionCobroModeloDTO;
 	}
  
}