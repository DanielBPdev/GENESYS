package com.asopagos.listaschequeo.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.ElementoListaDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/tiposSolicitante/cajaCompensacion
 */
public class ConsultarClasificacionesHabilitadas extends ServiceClient {
 
  
  	private String tipoSolicitante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ElementoListaDTO> result;
  
 	public ConsultarClasificacionesHabilitadas (String tipoSolicitante){
 		super();
		this.tipoSolicitante=tipoSolicitante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoSolicitante", tipoSolicitante)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ElementoListaDTO>) response.readEntity(new GenericType<List<ElementoListaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ElementoListaDTO> getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (String tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public String getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  
}