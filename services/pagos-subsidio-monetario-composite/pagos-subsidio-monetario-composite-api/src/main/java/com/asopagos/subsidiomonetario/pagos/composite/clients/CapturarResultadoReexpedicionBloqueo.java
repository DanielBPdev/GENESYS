package com.asopagos.subsidiomonetario.pagos.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.ResultadoReexpedicionBloqueoOutDTO;
import com.asopagos.subsidiomonetario.pagos.dto.ResultadoReexpedicionBloqueoInDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PagosSubsidioMonetarioComposite/capturarResultadoReexpedicionBloqueo
 */
public class CapturarResultadoReexpedicionBloqueo extends ServiceClient { 
    	private List<ResultadoReexpedicionBloqueoInDTO> consulta;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ResultadoReexpedicionBloqueoOutDTO> result;
  
 	public CapturarResultadoReexpedicionBloqueo (List<ResultadoReexpedicionBloqueoInDTO> consulta){
 		super();
		this.consulta=consulta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(consulta == null ? null : Entity.json(consulta));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ResultadoReexpedicionBloqueoOutDTO>) response.readEntity(new GenericType<List<ResultadoReexpedicionBloqueoOutDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ResultadoReexpedicionBloqueoOutDTO> getResult() {
		return result;
	}

 
  
  	public void setConsulta (List<ResultadoReexpedicionBloqueoInDTO> consulta){
 		this.consulta=consulta;
 	}
 	
 	public List<ResultadoReexpedicionBloqueoInDTO> getConsulta (){
 		return consulta;
 	}
  
}