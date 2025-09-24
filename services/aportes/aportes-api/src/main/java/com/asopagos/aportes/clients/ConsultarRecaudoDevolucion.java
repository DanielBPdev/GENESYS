package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.AnalisisDevolucionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarRecaudoDevolucion
 */
public class ConsultarRecaudoDevolucion extends ServiceClient { 
   	private Long idSolicitudDevolucion;
   	private List<AnalisisDevolucionDTO> analisis;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AnalisisDevolucionDTO> result;
  
 	public ConsultarRecaudoDevolucion (Long idSolicitudDevolucion,List<AnalisisDevolucionDTO> analisis){
 		super();
		this.idSolicitudDevolucion=idSolicitudDevolucion;
		this.analisis=analisis;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudDevolucion", idSolicitudDevolucion)
			.request(MediaType.APPLICATION_JSON)
			.post(analisis == null ? null : Entity.json(analisis));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<AnalisisDevolucionDTO>) response.readEntity(new GenericType<List<AnalisisDevolucionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<AnalisisDevolucionDTO> getResult() {
		return result;
	}

 
  	public void setIdSolicitudDevolucion (Long idSolicitudDevolucion){
 		this.idSolicitudDevolucion=idSolicitudDevolucion;
 	}
 	
 	public Long getIdSolicitudDevolucion (){
 		return idSolicitudDevolucion;
 	}
  
  	public void setAnalisis (List<AnalisisDevolucionDTO> analisis){
 		this.analisis=analisis;
 	}
 	
 	public List<AnalisisDevolucionDTO> getAnalisis (){
 		return analisis;
 	}
  
}