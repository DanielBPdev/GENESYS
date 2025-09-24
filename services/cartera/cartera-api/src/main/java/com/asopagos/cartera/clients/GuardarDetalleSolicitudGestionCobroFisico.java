package com.asopagos.cartera.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.DetalleSolicitudGestionCobroModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarDetalleSolicitudGestionCobroFisico
 */
public class GuardarDetalleSolicitudGestionCobroFisico extends ServiceClient { 
   	private Long idSolicitudGlobal;
   	private DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DetalleSolicitudGestionCobroModeloDTO result;
  
 	public GuardarDetalleSolicitudGestionCobroFisico (Long idSolicitudGlobal,DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroModeloDTO){
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
		result = (DetalleSolicitudGestionCobroModeloDTO) response.readEntity(DetalleSolicitudGestionCobroModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DetalleSolicitudGestionCobroModeloDTO getResult() {
		return result;
	}

 
  	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  	public void setDetalleSolicitudGestionCobroModeloDTO (DetalleSolicitudGestionCobroModeloDTO detalleSolicitudGestionCobroModeloDTO){
 		this.detalleSolicitudGestionCobroModeloDTO=detalleSolicitudGestionCobroModeloDTO;
 	}
 	
 	public DetalleSolicitudGestionCobroModeloDTO getDetalleSolicitudGestionCobroModeloDTO (){
 		return detalleSolicitudGestionCobroModeloDTO;
 	}
  
}