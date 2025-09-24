package com.asopagos.subsidiomonetario.pagos.composite.clients;

import com.asopagos.clienteanibol.dto.ResultadoDispersionAnibolDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PagosSubsidioMonetarioComposite/ProcesarResultadoAnulacionSubsidioMonetario
 */
public class ProcesarResultadoAnulacionSubsidioMonetario extends ServiceClient {
    	private ResultadoDispersionAnibolDTO respuestaAnibol;
  
  
 	public ProcesarResultadoAnulacionSubsidioMonetario (ResultadoDispersionAnibolDTO respuestaAnibol){
 		super();
		this.respuestaAnibol=respuestaAnibol;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(respuestaAnibol == null ? null : Entity.json(respuestaAnibol));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setRespuestaAnibol (ResultadoDispersionAnibolDTO respuestaAnibol){
 		this.respuestaAnibol=respuestaAnibol;
 	}
 	
 	public ResultadoDispersionAnibolDTO getRespuestaAnibol (){
 		return respuestaAnibol;
 	}
  
}