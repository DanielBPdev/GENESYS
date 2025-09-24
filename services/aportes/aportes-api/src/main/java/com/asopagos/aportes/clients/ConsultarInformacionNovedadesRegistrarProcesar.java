package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.aportes.dto.InformacionPlanillasRegistrarProcesarDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarInformacionNovedadesRegistrarProcesar
 */
public class ConsultarInformacionNovedadesRegistrarProcesar extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<InformacionPlanillasRegistrarProcesarDTO> result;
  
 	public ConsultarInformacionNovedadesRegistrarProcesar (){
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
		this.result = (List<InformacionPlanillasRegistrarProcesarDTO>) response.readEntity(new GenericType<List<InformacionPlanillasRegistrarProcesarDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<InformacionPlanillasRegistrarProcesarDTO> getResult() {
		return result;
	}

 
  
}