package com.asopagos.novedades.clients;

import java.lang.Long;
import com.asopagos.novedades.dto.DatosNovedadAutomaticaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/{idSolicitudNovedad}/almacenarSolicitudNovedadPersonaMasivo
 */
public class AlmacenarSolicitudNovedadPersonaMasivo extends ServiceClient { 
  	private Long idSolicitudNovedad;
    	private DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO;
  
  
 	public AlmacenarSolicitudNovedadPersonaMasivo (Long idSolicitudNovedad,DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO){
 		super();
		this.idSolicitudNovedad=idSolicitudNovedad;
		this.datosNovedadAutomaticaDTO=datosNovedadAutomaticaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitudNovedad", idSolicitudNovedad)
			.request(MediaType.APPLICATION_JSON)
			.post(datosNovedadAutomaticaDTO == null ? null : Entity.json(datosNovedadAutomaticaDTO));
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
  
  
  	public void setDatosNovedadAutomaticaDTO (DatosNovedadAutomaticaDTO datosNovedadAutomaticaDTO){
 		this.datosNovedadAutomaticaDTO=datosNovedadAutomaticaDTO;
 	}
 	
 	public DatosNovedadAutomaticaDTO getDatosNovedadAutomaticaDTO (){
 		return datosNovedadAutomaticaDTO;
 	}
  
}