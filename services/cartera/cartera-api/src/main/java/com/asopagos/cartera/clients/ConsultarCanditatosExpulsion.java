package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import java.lang.Boolean;
import com.asopagos.dto.modelo.DetalleSolicitudGestionCobroModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarCanditatosExpulsion
 */
public class ConsultarCanditatosExpulsion extends ServiceClient { 
   	private TipoAccionCobroEnum tipoAccionCobro;
  	private Boolean validacionExclusion;
   	private List<DetalleSolicitudGestionCobroModeloDTO> lstDetalles;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleSolicitudGestionCobroModeloDTO> result;
  
 	public ConsultarCanditatosExpulsion (TipoAccionCobroEnum tipoAccionCobro,Boolean validacionExclusion,List<DetalleSolicitudGestionCobroModeloDTO> lstDetalles){
 		super();
		this.tipoAccionCobro=tipoAccionCobro;
		this.validacionExclusion=validacionExclusion;
		this.lstDetalles=lstDetalles;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoAccionCobro", tipoAccionCobro)
			.queryParam("validacionExclusion", validacionExclusion)
			.request(MediaType.APPLICATION_JSON)
			.post(lstDetalles == null ? null : Entity.json(lstDetalles));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<DetalleSolicitudGestionCobroModeloDTO>) response.readEntity(new GenericType<List<DetalleSolicitudGestionCobroModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<DetalleSolicitudGestionCobroModeloDTO> getResult() {
		return result;
	}

 
  	public void setTipoAccionCobro (TipoAccionCobroEnum tipoAccionCobro){
 		this.tipoAccionCobro=tipoAccionCobro;
 	}
 	
 	public TipoAccionCobroEnum getTipoAccionCobro (){
 		return tipoAccionCobro;
 	}
  	public void setValidacionExclusion (Boolean validacionExclusion){
 		this.validacionExclusion=validacionExclusion;
 	}
 	
 	public Boolean getValidacionExclusion (){
 		return validacionExclusion;
 	}
  
  	public void setLstDetalles (List<DetalleSolicitudGestionCobroModeloDTO> lstDetalles){
 		this.lstDetalles=lstDetalles;
 	}
 	
 	public List<DetalleSolicitudGestionCobroModeloDTO> getLstDetalles (){
 		return lstDetalles;
 	}
  
}