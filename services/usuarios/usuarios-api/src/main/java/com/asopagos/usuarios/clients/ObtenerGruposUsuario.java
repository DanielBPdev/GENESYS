package com.asopagos.usuarios.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.usuarios.dto.GrupoDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/usuarios/{nombreUsuario}/grupos
 */
public class ObtenerGruposUsuario extends ServiceClient {
 
  	private String nombreUsuario;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<GrupoDTO> result;
  
 	public ObtenerGruposUsuario (String nombreUsuario){
 		super();
		this.nombreUsuario=nombreUsuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("nombreUsuario", nombreUsuario)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<GrupoDTO>) response.readEntity(new GenericType<List<GrupoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<GrupoDTO> getResult() {
		return result;
	}

 	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  
  
}