package com.asopagos.afiliados.clients;

import java.lang.Long;
import java.lang.Boolean;
import com.asopagos.afiliados.dto.RespuestaServicioInfraestructuraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliados/inactivarActivarTipoTenencia/{idTipoTenencia}
 */
public class InactivarActivarTipoTenencia extends ServiceClient { 
  	private Long idTipoTenencia;
   	private Boolean activar;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaServicioInfraestructuraDTO result;
  
 	public InactivarActivarTipoTenencia (Long idTipoTenencia,Boolean activar){
 		super();
		this.idTipoTenencia=idTipoTenencia;
		this.activar=activar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTipoTenencia", idTipoTenencia)
			.queryParam("activar", activar)
			.request(MediaType.APPLICATION_JSON)
			.put(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
		result = (RespuestaServicioInfraestructuraDTO) response.readEntity(RespuestaServicioInfraestructuraDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RespuestaServicioInfraestructuraDTO getResult() {
		return result;
	}

 	public void setIdTipoTenencia (Long idTipoTenencia){
 		this.idTipoTenencia=idTipoTenencia;
 	}
 	
 	public Long getIdTipoTenencia (){
 		return idTipoTenencia;
 	}
  
  	public void setActivar (Boolean activar){
 		this.activar=activar;
 	}
 	
 	public Boolean getActivar (){
 		return activar;
 	}
  
  
}