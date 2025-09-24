package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.DetalleSolicitudGestionCobroModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarDetalleGestionCobroSolicitud
 */
public class ConsultarDetalleGestionCobroSolicitud extends ServiceClient {
 
  
  	private Long idSolicitudFisico;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleSolicitudGestionCobroModeloDTO> result;
  
 	public ConsultarDetalleGestionCobroSolicitud (Long idSolicitudFisico){
 		super();
		this.idSolicitudFisico=idSolicitudFisico;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitudFisico", idSolicitudFisico)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DetalleSolicitudGestionCobroModeloDTO>) response.readEntity(new GenericType<List<DetalleSolicitudGestionCobroModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DetalleSolicitudGestionCobroModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdSolicitudFisico (Long idSolicitudFisico){
 		this.idSolicitudFisico=idSolicitudFisico;
 	}
 	
 	public Long getIdSolicitudFisico (){
 		return idSolicitudFisico;
 	}
  
}