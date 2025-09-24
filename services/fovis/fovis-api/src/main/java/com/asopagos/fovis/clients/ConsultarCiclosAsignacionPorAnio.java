package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Short;
import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarCiclosAsignacionPorAnio
 */
public class ConsultarCiclosAsignacionPorAnio extends ServiceClient {
 
  
  	private Short anio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CicloAsignacionModeloDTO> result;
  
 	public ConsultarCiclosAsignacionPorAnio (Short anio){
 		super();
		this.anio=anio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("anio", anio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CicloAsignacionModeloDTO>) response.readEntity(new GenericType<List<CicloAsignacionModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CicloAsignacionModeloDTO> getResult() {
		return result;
	}

 
  	public void setAnio (Short anio){
 		this.anio=anio;
 	}
 	
 	public Short getAnio (){
 		return anio;
 	}
  
}