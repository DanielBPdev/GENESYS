package com.asopagos.novedades.fovis.clients;

import java.lang.Long;
import com.asopagos.novedades.fovis.dto.DatosNovedadAutomaticaFovisDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovis/almacenarSolicitudNovedadAutomaticaMasiva
 */
public class AlmacenarSolicitudNovedadAutomaticaMasiva extends ServiceClient { 
   	private Long idSolicitudNovedad;
   	private DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaFovisDTO;
  
  
 	public AlmacenarSolicitudNovedadAutomaticaMasiva (Long idSolicitudNovedad,DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaFovisDTO){
 		super();
		this.idSolicitudNovedad=idSolicitudNovedad;
		this.datosNovedadAutomaticaFovisDTO=datosNovedadAutomaticaFovisDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudNovedad", idSolicitudNovedad)
			.request(MediaType.APPLICATION_JSON)
			.post(datosNovedadAutomaticaFovisDTO == null ? null : Entity.json(datosNovedadAutomaticaFovisDTO));
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
  
  	public void setDatosNovedadAutomaticaFovisDTO (DatosNovedadAutomaticaFovisDTO datosNovedadAutomaticaFovisDTO){
 		this.datosNovedadAutomaticaFovisDTO=datosNovedadAutomaticaFovisDTO;
 	}
 	
 	public DatosNovedadAutomaticaFovisDTO getDatosNovedadAutomaticaFovisDTO (){
 		return datosNovedadAutomaticaFovisDTO;
 	}
  
}