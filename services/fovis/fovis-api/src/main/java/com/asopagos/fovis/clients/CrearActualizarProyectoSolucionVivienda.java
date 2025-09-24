package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.ProyectoSolucionViviendaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/crearActualizarProyectoSolucionVivienda
 */
public class CrearActualizarProyectoSolucionVivienda extends ServiceClient { 
    	private ProyectoSolucionViviendaModeloDTO proyectoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ProyectoSolucionViviendaModeloDTO result;
  
 	public CrearActualizarProyectoSolucionVivienda (ProyectoSolucionViviendaModeloDTO proyectoDTO){
 		super();
		this.proyectoDTO=proyectoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(proyectoDTO == null ? null : Entity.json(proyectoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ProyectoSolucionViviendaModeloDTO) response.readEntity(ProyectoSolucionViviendaModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ProyectoSolucionViviendaModeloDTO getResult() {
		return result;
	}

 
  
  	public void setProyectoDTO (ProyectoSolucionViviendaModeloDTO proyectoDTO){
 		this.proyectoDTO=proyectoDTO;
 	}
 	
 	public ProyectoSolucionViviendaModeloDTO getProyectoDTO (){
 		return proyectoDTO;
 	}
  
}