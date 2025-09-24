package com.asopagos.novedades.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.SolicitudNovedadGeneralDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/obtenerNovedadesSinInstanciaProceso
 */
public class ObtenerNovedadesSinInstanciaProceso extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudNovedadGeneralDTO> result;
  
 	public ObtenerNovedadesSinInstanciaProceso (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudNovedadGeneralDTO>) response.readEntity(new GenericType<List<SolicitudNovedadGeneralDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudNovedadGeneralDTO> getResult() {
		return result;
	}

 
  
}