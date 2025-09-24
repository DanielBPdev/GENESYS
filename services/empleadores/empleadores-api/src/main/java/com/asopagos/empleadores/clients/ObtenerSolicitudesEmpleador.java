package com.asopagos.empleadores.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.Vista360EmpleadorRespuestaDTO;
import com.asopagos.dto.Vista360EmpleadorBusquedaParamsDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/obtenerSolicitudesEmpleador
 */
public class ObtenerSolicitudesEmpleador extends ServiceClient { 
    	private Vista360EmpleadorBusquedaParamsDTO params;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Vista360EmpleadorRespuestaDTO> result;
  
 	public ObtenerSolicitudesEmpleador (Vista360EmpleadorBusquedaParamsDTO params){
 		super();
		this.params=params;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(params == null ? null : Entity.json(params));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Vista360EmpleadorRespuestaDTO>) response.readEntity(new GenericType<List<Vista360EmpleadorRespuestaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Vista360EmpleadorRespuestaDTO> getResult() {
		return result;
	}

 
  
  	public void setParams (Vista360EmpleadorBusquedaParamsDTO params){
 		this.params=params;
 	}
 	
 	public Vista360EmpleadorBusquedaParamsDTO getParams (){
 		return params;
 	}
  
}