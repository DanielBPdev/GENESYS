package com.asopagos.constantes.parametros.clients;

import com.asopagos.constantes.parametros.dto.ConstantesParametroDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/constantesparametros/crearModificarConstanteParametro
 */
public class CrearModificarConstanteParametro extends ServiceClient { 
    	private ConstantesParametroDTO constanteParametroDTO;
  
  
 	public CrearModificarConstanteParametro (ConstantesParametroDTO constanteParametroDTO){
 		super();
		this.constanteParametroDTO=constanteParametroDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(constanteParametroDTO == null ? null : Entity.json(constanteParametroDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setConstanteParametroDTO (ConstantesParametroDTO constanteParametroDTO){
 		this.constanteParametroDTO=constanteParametroDTO;
 	}
 	
 	public ConstantesParametroDTO getConstanteParametroDTO (){
 		return constanteParametroDTO;
 	}
  
}