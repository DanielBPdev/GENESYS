package com.asopagos.fovis.clients;

import com.asopagos.dto.DocumentoAdministracionEstadoSolicitudDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/registrarDocumentoSolicitud
 */
public class RegistrarDocumentoSolicitud extends ServiceClient { 
    	private DocumentoAdministracionEstadoSolicitudDTO docAdminEstadoSolicitudDTO;
  
  
 	public RegistrarDocumentoSolicitud (DocumentoAdministracionEstadoSolicitudDTO docAdminEstadoSolicitudDTO){
 		super();
		this.docAdminEstadoSolicitudDTO=docAdminEstadoSolicitudDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(docAdminEstadoSolicitudDTO == null ? null : Entity.json(docAdminEstadoSolicitudDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDocAdminEstadoSolicitudDTO (DocumentoAdministracionEstadoSolicitudDTO docAdminEstadoSolicitudDTO){
 		this.docAdminEstadoSolicitudDTO=docAdminEstadoSolicitudDTO;
 	}
 	
 	public DocumentoAdministracionEstadoSolicitudDTO getDocAdminEstadoSolicitudDTO (){
 		return docAdminEstadoSolicitudDTO;
 	}
  
}