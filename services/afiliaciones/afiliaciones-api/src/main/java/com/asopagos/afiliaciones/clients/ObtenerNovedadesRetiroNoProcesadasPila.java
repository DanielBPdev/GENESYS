package com.asopagos.afiliaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.AfiliadoNovedadRetiroNoAplicadaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/obtenerNovedadesRetiroNoProcesadasPila
 */
public class ObtenerNovedadesRetiroNoProcesadasPila extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AfiliadoNovedadRetiroNoAplicadaDTO> result;
  
 	public ObtenerNovedadesRetiroNoProcesadasPila (){
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
		this.result = (List<AfiliadoNovedadRetiroNoAplicadaDTO>) response.readEntity(new GenericType<List<AfiliadoNovedadRetiroNoAplicadaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<AfiliadoNovedadRetiroNoAplicadaDTO> getResult() {
		return result;
	}

 
  
}