package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.DetalleSolicitudGestionCobroModeloDTO;
import com.asopagos.cartera.dto.FiltroDetalleSolicitudGestionCobroDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarDetalleSolicitudGestionCobro
 */
public class ConsultarDetalleSolicitudGestionCobro extends ServiceClient { 
    	private List<FiltroDetalleSolicitudGestionCobroDTO> filtroDetalleSolicitudGestion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleSolicitudGestionCobroModeloDTO> result;
  
 	public ConsultarDetalleSolicitudGestionCobro (List<FiltroDetalleSolicitudGestionCobroDTO> filtroDetalleSolicitudGestion){
 		super();
		this.filtroDetalleSolicitudGestion=filtroDetalleSolicitudGestion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(filtroDetalleSolicitudGestion == null ? null : Entity.json(filtroDetalleSolicitudGestion));
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

 
  
  	public void setFiltroDetalleSolicitudGestion (List<FiltroDetalleSolicitudGestionCobroDTO> filtroDetalleSolicitudGestion){
 		this.filtroDetalleSolicitudGestion=filtroDetalleSolicitudGestion;
 	}
 	
 	public List<FiltroDetalleSolicitudGestionCobroDTO> getFiltroDetalleSolicitudGestion (){
 		return filtroDetalleSolicitudGestion;
 	}
  
}