package com.asopagos.listaschequeo.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.util.List;
import com.asopagos.listaschequeo.dto.TransaccionRequisitoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/tiposSolicitante/consultarTransaccionesProceso
 */
public class ConsultarTransaccionesProceso extends ServiceClient {
 
  
  	private ProcesoEnum proceso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TransaccionRequisitoDTO> result;
  
 	public ConsultarTransaccionesProceso (ProcesoEnum proceso){
 		super();
		this.proceso=proceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("proceso", proceso)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<TransaccionRequisitoDTO>) response.readEntity(new GenericType<List<TransaccionRequisitoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<TransaccionRequisitoDTO> getResult() {
		return result;
	}

 
  	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  
}