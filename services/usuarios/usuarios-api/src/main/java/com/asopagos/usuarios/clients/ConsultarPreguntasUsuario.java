package com.asopagos.usuarios.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.usuarios.dto.PreguntaUsuarioDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/usuarios/preguntas
 */
public class ConsultarPreguntasUsuario extends ServiceClient {
 
  
  	private boolean respuestas;
  	private String nombreUsuario;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PreguntaUsuarioDTO> result;
  
 	public ConsultarPreguntasUsuario (boolean respuestas,String nombreUsuario){
 		super();
		this.respuestas=respuestas;
		this.nombreUsuario=nombreUsuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("respuestas", respuestas)
						.queryParam("nombreUsuario", nombreUsuario)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PreguntaUsuarioDTO>) response.readEntity(new GenericType<List<PreguntaUsuarioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PreguntaUsuarioDTO> getResult() {
		return result;
	}

 
  	public void setRespuestas (boolean respuestas){
 		this.respuestas=respuestas;
 	}
 	
 	public boolean getRespuestas (){
 		return respuestas;
 	}
  	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  
}