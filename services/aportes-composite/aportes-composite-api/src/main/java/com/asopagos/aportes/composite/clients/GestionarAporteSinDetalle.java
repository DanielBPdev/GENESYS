package com.asopagos.aportes.composite.clients;

import com.asopagos.dto.AnalisisDevolucionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/gestionarAporteSinDetalle
 */
public class GestionarAporteSinDetalle extends ServiceClient { 
    	private AnalisisDevolucionDTO analisisDevolucionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private AnalisisDevolucionDTO result;
  
 	public GestionarAporteSinDetalle (AnalisisDevolucionDTO analisisDevolucionDTO){
 		super();
		this.analisisDevolucionDTO=analisisDevolucionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(analisisDevolucionDTO == null ? null : Entity.json(analisisDevolucionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (AnalisisDevolucionDTO) response.readEntity(AnalisisDevolucionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public AnalisisDevolucionDTO getResult() {
		return result;
	}

 
  
  	public void setAnalisisDevolucionDTO (AnalisisDevolucionDTO analisisDevolucionDTO){
 		this.analisisDevolucionDTO=analisisDevolucionDTO;
 	}
 	
 	public AnalisisDevolucionDTO getAnalisisDevolucionDTO (){
 		return analisisDevolucionDTO;
 	}
  
}