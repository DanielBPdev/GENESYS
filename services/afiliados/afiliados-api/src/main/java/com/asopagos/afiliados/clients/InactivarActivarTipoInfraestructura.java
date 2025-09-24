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
 * /rest/afiliados/inactivarActivarTipoInfraestructura/{idTipoInfraestructura}
 */
public class InactivarActivarTipoInfraestructura extends ServiceClient { 
  	private Long idTipoInfraestructura;
   	private Boolean activar;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaServicioInfraestructuraDTO result;
  
 	public InactivarActivarTipoInfraestructura (Long idTipoInfraestructura,Boolean activar){
 		super();
		this.idTipoInfraestructura=idTipoInfraestructura;
		this.activar=activar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTipoInfraestructura", idTipoInfraestructura)
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

 	public void setIdTipoInfraestructura (Long idTipoInfraestructura){
 		this.idTipoInfraestructura=idTipoInfraestructura;
 	}
 	
 	public Long getIdTipoInfraestructura (){
 		return idTipoInfraestructura;
 	}
  
  	public void setActivar (Boolean activar){
 		this.activar=activar;
 	}
 	
 	public Boolean getActivar (){
 		return activar;
 	}
  
  
}