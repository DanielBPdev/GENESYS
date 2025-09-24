package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.RecursoComplementarioModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/crearActualizarRecursoComplementario/{idPostulacion}
 */
public class CrearActualizarRecursoComplementario extends ServiceClient { 
  	private Long idPostulacion;
    	private List<RecursoComplementarioModeloDTO> recursosComplementariosDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RecursoComplementarioModeloDTO> result;
  
 	public CrearActualizarRecursoComplementario (Long idPostulacion,List<RecursoComplementarioModeloDTO> recursosComplementariosDTO){
 		super();
		this.idPostulacion=idPostulacion;
		this.recursosComplementariosDTO=recursosComplementariosDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idPostulacion", idPostulacion)
			.request(MediaType.APPLICATION_JSON)
			.post(recursosComplementariosDTO == null ? null : Entity.json(recursosComplementariosDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<RecursoComplementarioModeloDTO>) response.readEntity(new GenericType<List<RecursoComplementarioModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<RecursoComplementarioModeloDTO> getResult() {
		return result;
	}

 	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  
  
  	public void setRecursosComplementariosDTO (List<RecursoComplementarioModeloDTO> recursosComplementariosDTO){
 		this.recursosComplementariosDTO=recursosComplementariosDTO;
 	}
 	
 	public List<RecursoComplementarioModeloDTO> getRecursosComplementariosDTO (){
 		return recursosComplementariosDTO;
 	}
  
}