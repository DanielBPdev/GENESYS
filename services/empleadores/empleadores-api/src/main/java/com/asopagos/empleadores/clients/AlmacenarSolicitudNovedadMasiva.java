package com.asopagos.empleadores.clients;

import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/{idSolicitudNovedad}/almacenarSolicitudNovedadMasiva
 */
public class AlmacenarSolicitudNovedadMasiva extends ServiceClient { 
  	private Long idSolicitudNovedad;
    	private List<Long> idEmpleadores;
  
  
 	public AlmacenarSolicitudNovedadMasiva (Long idSolicitudNovedad,List<Long> idEmpleadores){
 		super();
		this.idSolicitudNovedad=idSolicitudNovedad;
		this.idEmpleadores=idEmpleadores;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudNovedad", idSolicitudNovedad)
			.request(MediaType.APPLICATION_JSON)
			.post(idEmpleadores == null ? null : Entity.json(idEmpleadores));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdSolicitudNovedad (Long idSolicitudNovedad){
 		this.idSolicitudNovedad=idSolicitudNovedad;
 	}
 	
 	public Long getIdSolicitudNovedad (){
 		return idSolicitudNovedad;
 	}
  
  
  	public void setIdEmpleadores (List<Long> idEmpleadores){
 		this.idEmpleadores=idEmpleadores;
 	}
 	
 	public List<Long> getIdEmpleadores (){
 		return idEmpleadores;
 	}
  
}