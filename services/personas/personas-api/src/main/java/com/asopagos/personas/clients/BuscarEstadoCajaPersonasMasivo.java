package com.asopagos.personas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.personas.dto.ConsultaEstadoCajaPersonaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/buscarEstadoCajaPersonasMasivo
 */
public class BuscarEstadoCajaPersonasMasivo extends ServiceClient { 
    	private List<ConsultaEstadoCajaPersonaDTO> lstPersonas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConsultaEstadoCajaPersonaDTO> result;
  
 	public BuscarEstadoCajaPersonasMasivo (List<ConsultaEstadoCajaPersonaDTO> lstPersonas){
 		super();
		this.lstPersonas=lstPersonas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(lstPersonas == null ? null : Entity.json(lstPersonas));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ConsultaEstadoCajaPersonaDTO>) response.readEntity(new GenericType<List<ConsultaEstadoCajaPersonaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ConsultaEstadoCajaPersonaDTO> getResult() {
		return result;
	}

 
  
  	public void setLstPersonas (List<ConsultaEstadoCajaPersonaDTO> lstPersonas){
 		this.lstPersonas=lstPersonas;
 	}
 	
 	public List<ConsultaEstadoCajaPersonaDTO> getLstPersonas (){
 		return lstPersonas;
 	}
  
}