package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.aportes.dto.SolicitudAporteHistoricoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarHistoricoSolicitudes
 */
public class ConsultarHistoricoSolicitudes extends ServiceClient { 
    	private SolicitudAporteHistoricoDTO solicitudAporteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudAporteHistoricoDTO> result;
  
 	public ConsultarHistoricoSolicitudes (SolicitudAporteHistoricoDTO solicitudAporteDTO){
 		super();
		this.solicitudAporteDTO=solicitudAporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudAporteDTO == null ? null : Entity.json(solicitudAporteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<SolicitudAporteHistoricoDTO>) response.readEntity(new GenericType<List<SolicitudAporteHistoricoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<SolicitudAporteHistoricoDTO> getResult() {
		return result;
	}

 
  
  	public void setSolicitudAporteDTO (SolicitudAporteHistoricoDTO solicitudAporteDTO){
 		this.solicitudAporteDTO=solicitudAporteDTO;
 	}
 	
 	public SolicitudAporteHistoricoDTO getSolicitudAporteDTO (){
 		return solicitudAporteDTO;
 	}
  
}