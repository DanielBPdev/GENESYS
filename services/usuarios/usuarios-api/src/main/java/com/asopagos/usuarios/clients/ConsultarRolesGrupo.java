package com.asopagos.usuarios.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.usuarios.dto.RolDTO;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/grupos/roles/{idGrupo}
 */
public class ConsultarRolesGrupo extends ServiceClient {
 
  	private String idGrupo;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RolDTO> result;
  
 	public ConsultarRolesGrupo (String idGrupo){
 		super();
		this.idGrupo=idGrupo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idGrupo", idGrupo)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RolDTO>) response.readEntity(new GenericType<List<RolDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RolDTO> getResult() {
		return result;
	}

 	public void setIdGrupo (String idGrupo){
 		this.idGrupo=idGrupo;
 	}
 	
 	public String getIdGrupo (){
 		return idGrupo;
 	}
  
  
}