package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.SimulacionDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{numeroRadicacion}/consultarDetalleSolicitudesIndividuales
 */
public class ConsultarDetalleSolicitudesIndividuales extends ServiceClient {
 
  	private String numeroRadicacion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SimulacionDTO> result;
  
 	public ConsultarDetalleSolicitudesIndividuales (String numeroRadicacion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SimulacionDTO>) response.readEntity(new GenericType<List<SimulacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SimulacionDTO> getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  
}