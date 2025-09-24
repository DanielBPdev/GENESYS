package com.asopagos.usuarios.clients;

import com.asopagos.usuarios.dto.UsuarioCCF;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/usuarios/ccf/{nombreUsuario}
 */
public class ObtenerDatosUsuarioCajaCompensacion extends ServiceClient {
 
  	private String nombreUsuario;
  
  	private String primerApellido;
  	private String primerNombre;
  	private Boolean roles;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private UsuarioCCF result;
  
 	public ObtenerDatosUsuarioCajaCompensacion (String nombreUsuario,String primerApellido,String primerNombre,Boolean roles){
 		super();
		this.nombreUsuario=nombreUsuario;
		this.primerApellido=primerApellido;
		this.primerNombre=primerNombre;
		this.roles=roles;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("nombreUsuario", nombreUsuario)
									.queryParam("primerApellido", primerApellido)
						.queryParam("primerNombre", primerNombre)
						.queryParam("roles", roles)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (UsuarioCCF) response.readEntity(UsuarioCCF.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public UsuarioCCF getResult() {
		return result;
	}

 	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  
  	public void setPrimerApellido (String primerApellido){
 		this.primerApellido=primerApellido;
 	}
 	
 	public String getPrimerApellido (){
 		return primerApellido;
 	}
  	public void setPrimerNombre (String primerNombre){
 		this.primerNombre=primerNombre;
 	}
 	
 	public String getPrimerNombre (){
 		return primerNombre;
 	}
  	public void setRoles (Boolean roles){
 		this.roles=roles;
 	}
 	
 	public Boolean getRoles (){
 		return roles;
 	}
  
}