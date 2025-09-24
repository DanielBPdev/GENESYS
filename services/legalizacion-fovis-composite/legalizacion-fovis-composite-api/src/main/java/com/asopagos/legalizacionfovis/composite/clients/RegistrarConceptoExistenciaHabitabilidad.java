package com.asopagos.legalizacionfovis.composite.clients;

import com.asopagos.dto.fovis.RegistroExistenciaHabitabilidadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovisComposite/registrarConceptoExistenciaHabitabilidad
 */
public class RegistrarConceptoExistenciaHabitabilidad extends ServiceClient { 
    	private RegistroExistenciaHabitabilidadDTO existenciaHabitabilidadDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RegistroExistenciaHabitabilidadDTO result;
  
 	public RegistrarConceptoExistenciaHabitabilidad (RegistroExistenciaHabitabilidadDTO existenciaHabitabilidadDTO){
 		super();
		this.existenciaHabitabilidadDTO=existenciaHabitabilidadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(existenciaHabitabilidadDTO == null ? null : Entity.json(existenciaHabitabilidadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (RegistroExistenciaHabitabilidadDTO) response.readEntity(RegistroExistenciaHabitabilidadDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public RegistroExistenciaHabitabilidadDTO getResult() {
		return result;
	}

 
  
  	public void setExistenciaHabitabilidadDTO (RegistroExistenciaHabitabilidadDTO existenciaHabitabilidadDTO){
 		this.existenciaHabitabilidadDTO=existenciaHabitabilidadDTO;
 	}
 	
 	public RegistroExistenciaHabitabilidadDTO getExistenciaHabitabilidadDTO (){
 		return existenciaHabitabilidadDTO;
 	}
  
}