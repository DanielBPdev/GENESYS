package com.asopagos.solicitudes.clients;

import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import java.util.List;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudes/guardarDocAdminsSolicitudes/{numeroRadicado}
 */
public class GuardarDocumentosAdminSolicitudes extends ServiceClient { 
  	private String numeroRadicado;
    	private List<DocumentoAdministracionEstadoSolicitudDTO> administracionEstadoSolicituds;
  
  
 	public GuardarDocumentosAdminSolicitudes (String numeroRadicado,List<DocumentoAdministracionEstadoSolicitudDTO> administracionEstadoSolicituds){
 		super();
		this.numeroRadicado=numeroRadicado;
		this.administracionEstadoSolicituds=administracionEstadoSolicituds;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicado", numeroRadicado)
			.request(MediaType.APPLICATION_JSON)
			.post(administracionEstadoSolicituds == null ? null : Entity.json(administracionEstadoSolicituds));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
  
  	public void setAdministracionEstadoSolicituds (List<DocumentoAdministracionEstadoSolicitudDTO> administracionEstadoSolicituds){
 		this.administracionEstadoSolicituds=administracionEstadoSolicituds;
 	}
 	
 	public List<DocumentoAdministracionEstadoSolicitudDTO> getAdministracionEstadoSolicituds (){
 		return administracionEstadoSolicituds;
 	}
  
}