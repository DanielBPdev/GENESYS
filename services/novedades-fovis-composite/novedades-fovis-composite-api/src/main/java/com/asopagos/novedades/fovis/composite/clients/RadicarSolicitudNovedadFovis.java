package com.asopagos.novedades.fovis.composite.clients;

import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovisComposite/radicarSolicitudNovedad
 */
public class RadicarSolicitudNovedadFovis extends ServiceClient { 
    	private SolicitudNovedadFovisDTO infoSolicitud;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudNovedadFovisDTO result;
  
 	public RadicarSolicitudNovedadFovis (SolicitudNovedadFovisDTO infoSolicitud){
 		super();
		this.infoSolicitud=infoSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(infoSolicitud == null ? null : Entity.json(infoSolicitud));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudNovedadFovisDTO) response.readEntity(SolicitudNovedadFovisDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudNovedadFovisDTO getResult() {
		return result;
	}

 
  
  	public void setInfoSolicitud (SolicitudNovedadFovisDTO infoSolicitud){
 		this.infoSolicitud=infoSolicitud;
 	}
 	
 	public SolicitudNovedadFovisDTO getInfoSolicitud (){
 		return infoSolicitud;
 	}
  
}