package com.asopagos.usuarios.clients;

import java.util.List;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/usuarios/actualizarEstadoUsuarioMasivo/{estado}
 */
public class ActualizarEstadoUsuarioMasivo extends ServiceClient { 
  	private boolean estado;
    	private List<String> lstNombreUsuario;
  
  
 	public ActualizarEstadoUsuarioMasivo (boolean estado,List<String> lstNombreUsuario){
 		super();
		this.estado=estado;
		this.lstNombreUsuario=lstNombreUsuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("estado", estado)
			.request(MediaType.APPLICATION_JSON)
			.put(lstNombreUsuario == null ? null : Entity.json(lstNombreUsuario));
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
  
  
  	public void setLstNombreUsuario (List<String> lstNombreUsuario){
 		this.lstNombreUsuario=lstNombreUsuario;
 	}
 	
 	public List<String> getLstNombreUsuario (){
 		return lstNombreUsuario;
 	}
  
}