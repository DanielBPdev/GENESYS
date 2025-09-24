package com.asopagos.novedades.clients;

import com.asopagos.novedades.dto.DatosExcepcionNovedadDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/guardarExcepcionNovedad
 */
public class GuardarExcepcionNovedad extends ServiceClient { 
   	private String excepcion;
   	private DatosExcepcionNovedadDTO solicitudNovedadDTO;
  
  
 	public GuardarExcepcionNovedad (String excepcion,DatosExcepcionNovedadDTO solicitudNovedadDTO){
 		super();
		this.excepcion=excepcion;
		this.solicitudNovedadDTO=solicitudNovedadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("excepcion", excepcion)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudNovedadDTO == null ? null : Entity.json(solicitudNovedadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setExcepcion (String excepcion){
 		this.excepcion=excepcion;
 	}
 	
 	public String getExcepcion (){
 		return excepcion;
 	}
  
  	public void setSolicitudNovedadDTO (DatosExcepcionNovedadDTO solicitudNovedadDTO){
 		this.solicitudNovedadDTO=solicitudNovedadDTO;
 	}
 	
 	public DatosExcepcionNovedadDTO getSolicitudNovedadDTO (){
 		return solicitudNovedadDTO;
 	}
  
}