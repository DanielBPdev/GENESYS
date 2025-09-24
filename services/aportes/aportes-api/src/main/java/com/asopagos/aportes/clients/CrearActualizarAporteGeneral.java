package com.asopagos.aportes.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearActualizarAporteGeneral
 */
public class CrearActualizarAporteGeneral extends ServiceClient { 
    	private AporteGeneralModeloDTO aporteGeneralDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearActualizarAporteGeneral (AporteGeneralModeloDTO aporteGeneralDTO){
 		super();
		this.aporteGeneralDTO=aporteGeneralDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(aporteGeneralDTO == null ? null : Entity.json(aporteGeneralDTO));
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

 
  
  	public void setAporteGeneralDTO (AporteGeneralModeloDTO aporteGeneralDTO){
 		this.aporteGeneralDTO=aporteGeneralDTO;
 	}
 	
 	public AporteGeneralModeloDTO getAporteGeneralDTO (){
 		return aporteGeneralDTO;
 	}
  
}