package com.asopagos.asignaciones.clients;

import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/asignacionSolicitud/{procesoEnum}/asignaciones
 */
public class EjecutarAsignacion extends ServiceClient { 
  	private ProcesoEnum procesoEnum;
   	private Long sede;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public EjecutarAsignacion (ProcesoEnum procesoEnum,Long sede){
 		super();
		this.procesoEnum=procesoEnum;
		this.sede=sede;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("procesoEnum", procesoEnum)
			.queryParam("sede", sede)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public String getResult() {
		return result;
	}

 	public void setProcesoEnum (ProcesoEnum procesoEnum){
 		this.procesoEnum=procesoEnum;
 	}
 	
 	public ProcesoEnum getProcesoEnum (){
 		return procesoEnum;
 	}
  
  	public void setSede (Long sede){
 		this.sede=sede;
 	}
 	
 	public Long getSede (){
 		return sede;
 	}
  
  
}