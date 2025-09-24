package com.asopagos.personas.clients;

import com.asopagos.dto.modelo.JefeHogarModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/personas/crearActualizarJefeHogar
 */
public class CrearActualizarJefeHogar extends ServiceClient { 
    	private JefeHogarModeloDTO jefeHogarDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private JefeHogarModeloDTO result;
  
 	public CrearActualizarJefeHogar (JefeHogarModeloDTO jefeHogarDTO){
 		super();
		this.jefeHogarDTO=jefeHogarDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(jefeHogarDTO == null ? null : Entity.json(jefeHogarDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (JefeHogarModeloDTO) response.readEntity(JefeHogarModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public JefeHogarModeloDTO getResult() {
		return result;
	}

 
  
  	public void setJefeHogarDTO (JefeHogarModeloDTO jefeHogarDTO){
 		this.jefeHogarDTO=jefeHogarDTO;
 	}
 	
 	public JefeHogarModeloDTO getJefeHogarDTO (){
 		return jefeHogarDTO;
 	}
  
}