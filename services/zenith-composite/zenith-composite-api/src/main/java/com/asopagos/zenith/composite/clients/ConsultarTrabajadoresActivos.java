package com.asopagos.zenith.composite.clients;

import java.util.Date;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/zenithBusiness/consultarTrabajadoresActivos
 */
public class ConsultarTrabajadoresActivos extends ServiceClient {
 
  
  	private Date fechaDisponible;
  	private String nombreArchivo;
  	private String rutaUbicacion;
  
  
 	public ConsultarTrabajadoresActivos (Date fechaDisponible,String nombreArchivo,String rutaUbicacion){
 		super();
		this.fechaDisponible=fechaDisponible;
		this.nombreArchivo=nombreArchivo;
		this.rutaUbicacion=rutaUbicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("fechaDisponible", fechaDisponible)
						.queryParam("nombreArchivo", nombreArchivo)
						.queryParam("rutaUbicacion", rutaUbicacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setFechaDisponible (Date fechaDisponible){
 		this.fechaDisponible=fechaDisponible;
 	}
 	
 	public Date getFechaDisponible (){
 		return fechaDisponible;
 	}
  	public void setNombreArchivo (String nombreArchivo){
 		this.nombreArchivo=nombreArchivo;
 	}
 	
 	public String getNombreArchivo (){
 		return nombreArchivo;
 	}
  	public void setRutaUbicacion (String rutaUbicacion){
 		this.rutaUbicacion=rutaUbicacion;
 	}
 	
 	public String getRutaUbicacion (){
 		return rutaUbicacion;
 	}
  
}