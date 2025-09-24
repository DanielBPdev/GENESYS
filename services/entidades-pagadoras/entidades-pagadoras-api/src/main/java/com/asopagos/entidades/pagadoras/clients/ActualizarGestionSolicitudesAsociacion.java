package com.asopagos.entidades.pagadoras.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.pagadoras.dto.SolicitudAsociacionPersonaEntidadPagadoraDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/entidadesPagadoras/{idEntidadPagadora}/solicitudesAsociacionPersonas/gestionar
 */
public class ActualizarGestionSolicitudesAsociacion extends ServiceClient { 
  	private Long idEntidadPagadora;
    	private List<SolicitudAsociacionPersonaEntidadPagadoraDTO> solicitudes;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ActualizarGestionSolicitudesAsociacion (Long idEntidadPagadora,List<SolicitudAsociacionPersonaEntidadPagadoraDTO> solicitudes){
 		super();
		this.idEntidadPagadora=idEntidadPagadora;
		this.solicitudes=solicitudes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEntidadPagadora", idEntidadPagadora)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudes == null ? null : Entity.json(solicitudes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public String getResult() {
		return result;
	}

 	public void setIdEntidadPagadora (Long idEntidadPagadora){
 		this.idEntidadPagadora=idEntidadPagadora;
 	}
 	
 	public Long getIdEntidadPagadora (){
 		return idEntidadPagadora;
 	}
  
  
  	public void setSolicitudes (List<SolicitudAsociacionPersonaEntidadPagadoraDTO> solicitudes){
 		this.solicitudes=solicitudes;
 	}
 	
 	public List<SolicitudAsociacionPersonaEntidadPagadoraDTO> getSolicitudes (){
 		return solicitudes;
 	}
  
}