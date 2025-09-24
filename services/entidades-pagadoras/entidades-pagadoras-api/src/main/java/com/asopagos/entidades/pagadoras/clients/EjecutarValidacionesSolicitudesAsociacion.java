package com.asopagos.entidades.pagadoras.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.pagadoras.dto.SolicitudAsociacionPersonaEntidadPagadoraDTO;
import com.asopagos.enumeraciones.afiliaciones.TipoGestionSolicitudAsociacionEnum;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/entidadesPagadoras/{idEntidadPagadora}/solicitudesAsociacionPersonas/validarGestion/{TipoGestionSolicitudAsociacionEnum}
 */
public class EjecutarValidacionesSolicitudesAsociacion extends ServiceClient { 
  	private TipoGestionSolicitudAsociacionEnum TipoGestionSolicitudAsociacionEnum;
  	private Long idEntidadPagadora;
    	private List<SolicitudAsociacionPersonaEntidadPagadoraDTO> solicitudes;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public EjecutarValidacionesSolicitudesAsociacion (TipoGestionSolicitudAsociacionEnum TipoGestionSolicitudAsociacionEnum,Long idEntidadPagadora,List<SolicitudAsociacionPersonaEntidadPagadoraDTO> solicitudes){
 		super();
		this.TipoGestionSolicitudAsociacionEnum=TipoGestionSolicitudAsociacionEnum;
		this.idEntidadPagadora=idEntidadPagadora;
		this.solicitudes=solicitudes;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("TipoGestionSolicitudAsociacionEnum", TipoGestionSolicitudAsociacionEnum)
			.resolveTemplate("idEntidadPagadora", idEntidadPagadora)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudes == null ? null : Entity.json(solicitudes));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 	public void setTipoGestionSolicitudAsociacionEnum (TipoGestionSolicitudAsociacionEnum TipoGestionSolicitudAsociacionEnum){
 		this.TipoGestionSolicitudAsociacionEnum=TipoGestionSolicitudAsociacionEnum;
 	}
 	
 	public TipoGestionSolicitudAsociacionEnum getTipoGestionSolicitudAsociacionEnum (){
 		return TipoGestionSolicitudAsociacionEnum;
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