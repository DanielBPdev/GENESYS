package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.AhorroPrevioModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/crearActualizarAhorroPrevio/{idPostulacion}
 */
public class CrearActualizarAhorroPrevio extends ServiceClient { 
  	private Long idPostulacion;
    	private List<AhorroPrevioModeloDTO> ahorrosPreviosDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AhorroPrevioModeloDTO> result;
  
 	public CrearActualizarAhorroPrevio (Long idPostulacion,List<AhorroPrevioModeloDTO> ahorrosPreviosDTO){
 		super();
		this.idPostulacion=idPostulacion;
		this.ahorrosPreviosDTO=ahorrosPreviosDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idPostulacion", idPostulacion)
			.request(MediaType.APPLICATION_JSON)
			.post(ahorrosPreviosDTO == null ? null : Entity.json(ahorrosPreviosDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<AhorroPrevioModeloDTO>) response.readEntity(new GenericType<List<AhorroPrevioModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<AhorroPrevioModeloDTO> getResult() {
		return result;
	}

 	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  
  
  	public void setAhorrosPreviosDTO (List<AhorroPrevioModeloDTO> ahorrosPreviosDTO){
 		this.ahorrosPreviosDTO=ahorrosPreviosDTO;
 	}
 	
 	public List<AhorroPrevioModeloDTO> getAhorrosPreviosDTO (){
 		return ahorrosPreviosDTO;
 	}
  
}