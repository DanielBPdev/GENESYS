package com.asopagos.usuarios.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.enumeraciones.core.EstadoActivoInactivoEnum;
import java.util.List;
import java.lang.Long;
import com.asopagos.usuarios.dto.UsuarioDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/usuarios/sede/{idSede}
 */
public class ConsultarUsuarioSede extends ServiceClient {
 
  	private Long idSede;
  
  	private EstadoActivoInactivoEnum estado;
	  private boolean pertenecenCaJa;

  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<UsuarioDTO> result;
  
 	public ConsultarUsuarioSede (Long idSede,EstadoActivoInactivoEnum estado, boolean pertenecenCaJa){
 		super();
		this.idSede=idSede;
		this.estado=estado;
		this.pertenecenCaJa=pertenecenCaJa;
 	}

	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
				.resolveTemplate("idSede", idSede)
				.queryParam("estado", estado)
				.queryParam("pertenecenCaJa", pertenecenCaJa)
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

 	public void setIdSede (Long idSede){
 		this.idSede=idSede;
 	}
 	
 	public Long getIdSede (){
 		return idSede;
 	}
  
  	public void setEstado (EstadoActivoInactivoEnum estado){
 		this.estado=estado;
 	}
 	
 	public EstadoActivoInactivoEnum getEstado (){
 		return estado;
 	}
  
}