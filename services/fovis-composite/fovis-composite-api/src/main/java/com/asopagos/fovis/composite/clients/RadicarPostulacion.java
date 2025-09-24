package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/radicarPostulacion
 */
public class RadicarPostulacion extends ServiceClient { 
   	private Long idSolicitudGlobal;
  	private Boolean terminarTarea;
   	private SolicitudPostulacionFOVISDTO solicitudPostulacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudPostulacionFOVISDTO result;
  
 	public RadicarPostulacion (Long idSolicitudGlobal,Boolean terminarTarea,SolicitudPostulacionFOVISDTO solicitudPostulacionDTO){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
		this.terminarTarea=terminarTarea;
		this.solicitudPostulacionDTO=solicitudPostulacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudGlobal", idSolicitudGlobal)
			.queryParam("terminarTarea", terminarTarea)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudPostulacionDTO == null ? null : Entity.json(solicitudPostulacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudPostulacionFOVISDTO) response.readEntity(SolicitudPostulacionFOVISDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudPostulacionFOVISDTO getResult() {
		return result;
	}

 
  	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  	public void setTerminarTarea (Boolean terminarTarea){
 		this.terminarTarea=terminarTarea;
 	}
 	
 	public Boolean getTerminarTarea (){
 		return terminarTarea;
 	}
  
  	public void setSolicitudPostulacionDTO (SolicitudPostulacionFOVISDTO solicitudPostulacionDTO){
 		this.solicitudPostulacionDTO=solicitudPostulacionDTO;
 	}
 	
 	public SolicitudPostulacionFOVISDTO getSolicitudPostulacionDTO (){
 		return solicitudPostulacionDTO;
 	}
  
}