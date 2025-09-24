package com.asopagos.usuarios.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Boolean;
import com.asopagos.usuarios.dto.UsuarioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/revisar/usuariosRetirados
 */
public class BloquearUsuariosPendienteInactivacion extends ServiceClient { 
   	private Boolean consulta;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<UsuarioDTO> result;
  
 	public BloquearUsuariosPendienteInactivacion (Boolean consulta){
 		super();
		this.consulta=consulta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("consulta", consulta)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<UsuarioDTO>) response.readEntity(new GenericType<List<UsuarioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<UsuarioDTO> getResult() {
		return result;
	}

 
  	public void setConsulta (Boolean consulta){
 		this.consulta=consulta;
 	}
 	
 	public Boolean getConsulta (){
 		return consulta;
 	}
  
  
}