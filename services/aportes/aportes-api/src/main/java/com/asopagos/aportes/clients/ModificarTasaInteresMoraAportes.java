package com.asopagos.aportes.clients;

import com.asopagos.aportes.dto.ResultadoModificarTasaInteresDTO;
import com.asopagos.aportes.dto.ModificarTasaInteresMoraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/modificarTasaInteresMoraAportes
 */
public class ModificarTasaInteresMoraAportes extends ServiceClient { 
    	private ModificarTasaInteresMoraDTO tasaModificada;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoModificarTasaInteresDTO result;
  
 	public ModificarTasaInteresMoraAportes (ModificarTasaInteresMoraDTO tasaModificada){
 		super();
		this.tasaModificada=tasaModificada;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(tasaModificada == null ? null : Entity.json(tasaModificada));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoModificarTasaInteresDTO) response.readEntity(ResultadoModificarTasaInteresDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoModificarTasaInteresDTO getResult() {
		return result;
	}

 
  
  	public void setTasaModificada (ModificarTasaInteresMoraDTO tasaModificada){
 		this.tasaModificada=tasaModificada;
 	}
 	
 	public ModificarTasaInteresMoraDTO getTasaModificada (){
 		return tasaModificada;
 	}
  
}