package com.asopagos.novedades.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.PersonaDTO;
import com.asopagos.dto.PersonaPostulacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesFovis/consultarPostulacionVigentePersonas
 */
public class ConsultarPostulacionVigentePersonas extends ServiceClient { 
    	private List<PersonaDTO> listPersonas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PersonaPostulacionDTO> result;
  
 	public ConsultarPostulacionVigentePersonas (List<PersonaDTO> listPersonas){
 		super();
		this.listPersonas=listPersonas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listPersonas == null ? null : Entity.json(listPersonas));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<PersonaPostulacionDTO>) response.readEntity(new GenericType<List<PersonaPostulacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<PersonaPostulacionDTO> getResult() {
		return result;
	}

 
  
  	public void setListPersonas (List<PersonaDTO> listPersonas){
 		this.listPersonas=listPersonas;
 	}
 	
 	public List<PersonaDTO> getListPersonas (){
 		return listPersonas;
 	}
  
}