package com.asopagos.usuarios.clients;

import java.util.List;
import com.asopagos.usuarios.dto.PreguntaUsuarioDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/usuarios/preguntas
 */
public class ActualizarPreguntasUsuario extends ServiceClient { 
   	private String nombreUsuario;
   	private List<PreguntaUsuarioDTO> preguntas;
  
  
 	public ActualizarPreguntasUsuario (String nombreUsuario,List<PreguntaUsuarioDTO> preguntas){
 		super();
		this.nombreUsuario=nombreUsuario;
		this.preguntas=preguntas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("nombreUsuario", nombreUsuario)
			.request(MediaType.APPLICATION_JSON)
			.put(preguntas == null ? null : Entity.json(preguntas));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  
  	public void setPreguntas (List<PreguntaUsuarioDTO> preguntas){
 		this.preguntas=preguntas;
 	}
 	
 	public List<PreguntaUsuarioDTO> getPreguntas (){
 		return preguntas;
 	}
  
}