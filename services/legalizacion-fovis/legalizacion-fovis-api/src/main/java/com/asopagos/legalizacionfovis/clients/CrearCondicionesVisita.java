package com.asopagos.legalizacionfovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.CondicionVisitaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/crearCondicionesVisita
 */
public class CrearCondicionesVisita extends ServiceClient { 
    	private List<CondicionVisitaModeloDTO> condicionesVisitaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CondicionVisitaModeloDTO> result;
  
 	public CrearCondicionesVisita (List<CondicionVisitaModeloDTO> condicionesVisitaDTO){
 		super();
		this.condicionesVisitaDTO=condicionesVisitaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(condicionesVisitaDTO == null ? null : Entity.json(condicionesVisitaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<CondicionVisitaModeloDTO>) response.readEntity(new GenericType<List<CondicionVisitaModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<CondicionVisitaModeloDTO> getResult() {
		return result;
	}

 
  
  	public void setCondicionesVisitaDTO (List<CondicionVisitaModeloDTO> condicionesVisitaDTO){
 		this.condicionesVisitaDTO=condicionesVisitaDTO;
 	}
 	
 	public List<CondicionVisitaModeloDTO> getCondicionesVisitaDTO (){
 		return condicionesVisitaDTO;
 	}
  
}