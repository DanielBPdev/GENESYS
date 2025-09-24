package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearAporteGeneral
 */
public class CrearAporteGeneral extends ServiceClient { 
    	private AporteGeneralModeloDTO aporteGeneralDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private AporteGeneralModeloDTO result;
  
 	public CrearAporteGeneral (AporteGeneralModeloDTO aporteGeneralDTO){
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
		result = (AporteGeneralModeloDTO) response.readEntity(AporteGeneralModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public AporteGeneralModeloDTO getResult() {
		return result;
	}

 
  
  	public void setAporteGeneralDTO (AporteGeneralModeloDTO aporteGeneralDTO){
 		this.aporteGeneralDTO=aporteGeneralDTO;
 	}
 	
 	public AporteGeneralModeloDTO getAporteGeneralDTO (){
 		return aporteGeneralDTO;
 	}
  
}