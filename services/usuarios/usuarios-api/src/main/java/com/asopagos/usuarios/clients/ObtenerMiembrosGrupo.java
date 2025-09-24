package com.asopagos.usuarios.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.enumeraciones.usuarios.EstadoUsuarioEnum;
import java.util.List;
import java.lang.String;
import com.asopagos.usuarios.dto.UsuarioDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/grupos/{idGrupo}/miembros
 */
public class ObtenerMiembrosGrupo extends ServiceClient {
 
  	private String idGrupo;
  
  	private String sede;
  	private EstadoUsuarioEnum estado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<UsuarioDTO> result;
  
 	public ObtenerMiembrosGrupo (String idGrupo,String sede,EstadoUsuarioEnum estado){
 		super();
		this.idGrupo=idGrupo;
		this.sede=sede;
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idGrupo", idGrupo)
									.queryParam("sede", sede)
						.queryParam("estado", estado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<UsuarioDTO>) response.readEntity(new GenericType<List<UsuarioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<UsuarioDTO> getResult() {
		return result;
	}

 	public void setIdGrupo (String idGrupo){
 		this.idGrupo=idGrupo;
 	}
 	
 	public String getIdGrupo (){
 		return idGrupo;
 	}
  
  	public void setSede (String sede){
 		this.sede=sede;
 	}
 	
 	public String getSede (){
 		return sede;
 	}
  	public void setEstado (EstadoUsuarioEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoUsuarioEnum getEstado (){
 		return estado;
 	}
  
}