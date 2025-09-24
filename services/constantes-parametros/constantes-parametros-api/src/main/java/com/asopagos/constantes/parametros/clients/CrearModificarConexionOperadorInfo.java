package com.asopagos.constantes.parametros.clients;

import com.asopagos.dto.ConexionOperadorInformacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/constantesparametros/crearModificarConexionOperadorInfo
 */
public class CrearModificarConexionOperadorInfo extends ServiceClient { 
    	private ConexionOperadorInformacionDTO conexionOperadorInformacionDTO;
  
  
 	public CrearModificarConexionOperadorInfo (ConexionOperadorInformacionDTO conexionOperadorInformacionDTO){
 		super();
		this.conexionOperadorInformacionDTO=conexionOperadorInformacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(conexionOperadorInformacionDTO == null ? null : Entity.json(conexionOperadorInformacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setConexionOperadorInformacionDTO (ConexionOperadorInformacionDTO conexionOperadorInformacionDTO){
 		this.conexionOperadorInformacionDTO=conexionOperadorInformacionDTO;
 	}
 	
 	public ConexionOperadorInformacionDTO getConexionOperadorInformacionDTO (){
 		return conexionOperadorInformacionDTO;
 	}
  
}