package com.asopagos.fovis.composite.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.ActaAsignacionFOVISModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/asignacionFovisComposite/guardarInformacionYDocumentoActaAsignacion
 */
public class GuardarInformacionYDocumentoActaAsignacion extends ServiceClient { 
   	private Long idSolicitudGlobal;
   	private ActaAsignacionFOVISModeloDTO actaAsignacionFOVISModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ActaAsignacionFOVISModeloDTO result;
  
 	public GuardarInformacionYDocumentoActaAsignacion (Long idSolicitudGlobal,ActaAsignacionFOVISModeloDTO actaAsignacionFOVISModeloDTO){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
		this.actaAsignacionFOVISModeloDTO=actaAsignacionFOVISModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudGlobal", idSolicitudGlobal)
			.request(MediaType.APPLICATION_JSON)
			.post(actaAsignacionFOVISModeloDTO == null ? null : Entity.json(actaAsignacionFOVISModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ActaAsignacionFOVISModeloDTO) response.readEntity(ActaAsignacionFOVISModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ActaAsignacionFOVISModeloDTO getResult() {
		return result;
	}

 
  	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  	public void setActaAsignacionFOVISModeloDTO (ActaAsignacionFOVISModeloDTO actaAsignacionFOVISModeloDTO){
 		this.actaAsignacionFOVISModeloDTO=actaAsignacionFOVISModeloDTO;
 	}
 	
 	public ActaAsignacionFOVISModeloDTO getActaAsignacionFOVISModeloDTO (){
 		return actaAsignacionFOVISModeloDTO;
 	}
  
}