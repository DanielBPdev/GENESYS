package com.asopagos.usuarios.clients;

import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/usuarios/aceptarDerechos/{nombreUsuario}
 */
public class AceptarDerechosAcceso extends ServiceClient { 
  	private String nombreUsuario;
   	private Boolean debeAceptarTerminos;
   
  
 	public AceptarDerechosAcceso (String nombreUsuario,Boolean debeAceptarTerminos){
 		super();
		this.nombreUsuario=nombreUsuario;
		this.debeAceptarTerminos=debeAceptarTerminos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("nombreUsuario", nombreUsuario)
			.queryParam("debeAceptarTerminos", debeAceptarTerminos)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
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
  
  	public void setDebeAceptarTerminos (Boolean debeAceptarTerminos){
 		this.debeAceptarTerminos=debeAceptarTerminos;
 	}
 	
 	public Boolean getDebeAceptarTerminos (){
 		return debeAceptarTerminos;
 	}
  
  
}