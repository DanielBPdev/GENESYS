package com.asopagos.legalizacionfovis.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudLegalizacionDesembolsoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarSolicitudLegalizacionDesembolso
 */
public class ConsultarSolicitudLegalizacionDesembolso extends ServiceClient {
 
  
  	private Long idSolicitud;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudLegalizacionDesembolsoModeloDTO result;
  
 	public ConsultarSolicitudLegalizacionDesembolso (Long idSolicitud){
 		super();
		this.idSolicitud=idSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitud", idSolicitud)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudLegalizacionDesembolsoModeloDTO) response.readEntity(SolicitudLegalizacionDesembolsoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudLegalizacionDesembolsoModeloDTO getResult() {
		return result;
	}

 
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
}