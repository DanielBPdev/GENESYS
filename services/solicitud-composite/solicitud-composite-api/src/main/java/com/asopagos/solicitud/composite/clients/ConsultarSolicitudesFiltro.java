package com.asopagos.solicitud.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.FiltroSolicitudDTO;
import com.asopagos.dto.ResultadoConsultaSolicitudDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudComposite/consultarSolicitudesFiltro
 */
public class ConsultarSolicitudesFiltro extends ServiceClient { 
    	private FiltroSolicitudDTO filtroSolicitud;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ResultadoConsultaSolicitudDTO> result;
  
 	public ConsultarSolicitudesFiltro (FiltroSolicitudDTO filtroSolicitud){
 		super();
		this.filtroSolicitud=filtroSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(filtroSolicitud == null ? null : Entity.json(filtroSolicitud));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ResultadoConsultaSolicitudDTO>) response.readEntity(new GenericType<List<ResultadoConsultaSolicitudDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ResultadoConsultaSolicitudDTO> getResult() {
		return result;
	}

 
  
  	public void setFiltroSolicitud (FiltroSolicitudDTO filtroSolicitud){
 		this.filtroSolicitud=filtroSolicitud;
 	}
 	
 	public FiltroSolicitudDTO getFiltroSolicitud (){
 		return filtroSolicitud;
 	}
  
}