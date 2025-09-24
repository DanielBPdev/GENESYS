package com.asopagos.novedades.clients;

import java.lang.Long;
import com.asopagos.dto.CargueMultipleDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesCargueMultiple/registrarCargue/{idEmpleador}
 */
public class RegistrarCargue extends ServiceClient { 
  	private Long idEmpleador;
    	private CargueMultipleDTO cargueMultipleDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarCargue (Long idEmpleador,CargueMultipleDTO cargueMultipleDTO){
 		super();
		this.idEmpleador=idEmpleador;
		this.cargueMultipleDTO=cargueMultipleDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON)
			.post(cargueMultipleDTO == null ? null : Entity.json(cargueMultipleDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  
  	public void setCargueMultipleDTO (CargueMultipleDTO cargueMultipleDTO){
 		this.cargueMultipleDTO=cargueMultipleDTO;
 	}
 	
 	public CargueMultipleDTO getCargueMultipleDTO (){
 		return cargueMultipleDTO;
 	}
  
}