package com.asopagos.usuarios.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/usuarios/actualizarEstadoActivacionUsuario/{nombreUsuario}/{estado}
 */
public class ActualizarEstadoActivacionUsuario extends ServiceClient { 
  	private boolean estado;
  	private String nombreUsuario;
    
  
 	public ActualizarEstadoActivacionUsuario (boolean estado,String nombreUsuario){
 		super();
		this.estado=estado;
		this.nombreUsuario=nombreUsuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("estado", estado)
			.resolveTemplate("nombreUsuario", nombreUsuario)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setEstado (boolean estado){
 		this.estado=estado;
 	}
 	
 	public boolean getEstado (){
 		return estado;
 	}
  	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  
  
  
}