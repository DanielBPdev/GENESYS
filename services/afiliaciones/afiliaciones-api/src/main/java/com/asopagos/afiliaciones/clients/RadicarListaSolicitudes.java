package com.asopagos.afiliaciones.clients;

import java.util.List;
import java.lang.Long;
import java.util.Map;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliaciones/radicarListaSolicitudes
 */
public class RadicarListaSolicitudes extends ServiceClient { 
   	private List<Long> listSolicitudes;
   	private String sede;
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public RadicarListaSolicitudes (List<Long> listSolicitudes,String sede){
 		super();
		 this.listSolicitudes=listSolicitudes;
		this.sede=sede;
		
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("sede", sede)
			.request(MediaType.APPLICATION_JSON)
			.post(listSolicitudes == null ? null : Entity.json(listSolicitudes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,String> getResult() {
		return result;
	}

 	public void setListSolicitudes (List<Long> listSolicitudes){
 		this.listSolicitudes=listSolicitudes;
 	}
 	
 	public List<Long> getListSolicitudes (){
 		return listSolicitudes;
 	}
  	public void setSede (String sede){
 		this.sede=sede;
 	}
 	
 	public String getSede (){
 		return sede;
 	}
  
  
  
}