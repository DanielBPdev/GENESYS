package com.asopagos.entidades.pagadoras.clients;

import java.util.List;
import com.asopagos.entidades.pagadoras.dto.DocumentoEntidadPagadoraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/entidadesPagadoras/documentos
 */
public class GuardarDocumentosAdjuntos extends ServiceClient { 
    	private List<DocumentoEntidadPagadoraDTO> documentos;
  
  
 	public GuardarDocumentosAdjuntos (List<DocumentoEntidadPagadoraDTO> documentos){
 		super();
		this.documentos=documentos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(documentos == null ? null : Entity.json(documentos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDocumentos (List<DocumentoEntidadPagadoraDTO> documentos){
 		this.documentos=documentos;
 	}
 	
 	public List<DocumentoEntidadPagadoraDTO> getDocumentos (){
 		return documentos;
 	}
  
}