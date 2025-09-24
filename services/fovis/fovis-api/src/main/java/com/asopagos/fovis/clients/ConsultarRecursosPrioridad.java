package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/ConsultarRecursosPrioridad
 */
public class ConsultarRecursosPrioridad extends ServiceClient {
 
  
  	private Boolean estado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<String> result;
  
 	public ConsultarRecursosPrioridad (Boolean estado){
 		super();
		this.estado=estado;

 	}
 
  	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.queryParam("estado", estado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
 	@Override
	protected void getResultData(Response response) {
		this.result = (List<String>) response.readEntity(new GenericType<List<String>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<String> getResult() {
		return result;
	}

 
  	public void setEstado (Boolean estado){
 		this.estado=estado;
 	}
 	
 	public Boolean getEstado (){
 		return estado;
 	}

  
}