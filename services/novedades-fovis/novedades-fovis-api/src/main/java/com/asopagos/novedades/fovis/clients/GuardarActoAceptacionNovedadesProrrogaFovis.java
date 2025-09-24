package com.asopagos.novedades.fovis.clients;

import java.util.List;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovis/guardarActoAceptacionNovedadesProrrogaFovis
 */
public class GuardarActoAceptacionNovedadesProrrogaFovis extends ServiceClient { 
   	private Long fechaAprobacion;
  	private String numeroActoAdministrativo;
   	private List<Long> idSolicitudesNovedadFovis;
  
  
 	public GuardarActoAceptacionNovedadesProrrogaFovis (Long fechaAprobacion,String numeroActoAdministrativo,List<Long> idSolicitudesNovedadFovis){
 		super();
		this.fechaAprobacion=fechaAprobacion;
		this.numeroActoAdministrativo=numeroActoAdministrativo;
		this.idSolicitudesNovedadFovis=idSolicitudesNovedadFovis;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("fechaAprobacion", fechaAprobacion)
			.queryParam("numeroActoAdministrativo", numeroActoAdministrativo)
			.request(MediaType.APPLICATION_JSON)
			.post(idSolicitudesNovedadFovis == null ? null : Entity.json(idSolicitudesNovedadFovis));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setFechaAprobacion (Long fechaAprobacion){
 		this.fechaAprobacion=fechaAprobacion;
 	}
 	
 	public Long getFechaAprobacion (){
 		return fechaAprobacion;
 	}
  	public void setNumeroActoAdministrativo (String numeroActoAdministrativo){
 		this.numeroActoAdministrativo=numeroActoAdministrativo;
 	}
 	
 	public String getNumeroActoAdministrativo (){
 		return numeroActoAdministrativo;
 	}
  
  	public void setIdSolicitudesNovedadFovis (List<Long> idSolicitudesNovedadFovis){
 		this.idSolicitudesNovedadFovis=idSolicitudesNovedadFovis;
 	}
 	
 	public List<Long> getIdSolicitudesNovedadFovis (){
 		return idSolicitudesNovedadFovis;
 	}
  
}