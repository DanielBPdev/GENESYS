package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.entidades.subsidiomonetario.pagos.RegistroSolicitudAnibol;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarRegistroSolicitudAnibol
 */
public class ConsultarRegistroSolicitudAnibol extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RegistroSolicitudAnibol> result;
  
 	public ConsultarRegistroSolicitudAnibol (){
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
		this.result = (List<RegistroSolicitudAnibol>) response.readEntity(new GenericType<List<RegistroSolicitudAnibol>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RegistroSolicitudAnibol> getResult() {
		return result;
	}

 
  
}