package com.asopagos.usuarios.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.Date;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.dto.cargaMultiple.UsuarioGestionDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/usuarios/persona/{nombreUsuario}
 */
public class GestionConsultarPersona extends ServiceClient {
 
	private TipoIdentificacionEnum tipoDocumento;
	private String numeroIdentificacion;
	private String nombreUsuario;
  	private String primerApellido;
  	private String primerNombre;
  	private Boolean roles;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<UsuarioGestionDTO> result;
  
 	public GestionConsultarPersona (TipoIdentificacionEnum tipoDocumento, String numeroIdentificacion,String nombreUsuario,String primerApellido,String primerNombre,Boolean roles){
 		super();
		this.tipoDocumento=tipoDocumento;
		this.numeroIdentificacion=numeroIdentificacion;
		this.nombreUsuario=nombreUsuario;
		this.primerApellido=primerApellido;
		this.primerNombre=primerNombre;
		this.roles=roles;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.queryParam("nombreUsuario", nombreUsuario)
									.queryParam("tipoIdentificacion",tipoDocumento)
									.queryParam("numIdentificacion",numeroIdentificacion)
									.queryParam("primerApellido", primerApellido)
						.queryParam("primerNombre", primerNombre)
						.queryParam("roles", roles)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<UsuarioGestionDTO>) response.readEntity(new GenericType<List<UsuarioGestionDTO>>() {});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<UsuarioGestionDTO> getResult() {
		return result;
	}

	public void setTipoIdentificacion (TipoIdentificacionEnum tipoDocumento){
 		this.tipoDocumento=tipoDocumento;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoDocumento;
 	}

	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
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