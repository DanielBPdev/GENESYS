package com.asopagos.comunicados.clients;

import java.lang.Long;
import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalComunicado;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/comunicados/obtenerDatoTemporalComunicado
 */
public class ObtenerDatoTemporalComunicado extends ServiceClient { 
    	private Long idTarea;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatoTemporalComunicado result;
  
 	public ObtenerDatoTemporalComunicado (Long idTarea){
 		super();
		this.idTarea=idTarea;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idTarea == null ? null : Entity.json(idTarea));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (DatoTemporalComunicado) response.readEntity(DatoTemporalComunicado.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DatoTemporalComunicado getResult() {
		return result;
	}

 
  
  	public void setIdTarea (Long idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public Long getIdTarea (){
 		return idTarea;
 	}
  
}