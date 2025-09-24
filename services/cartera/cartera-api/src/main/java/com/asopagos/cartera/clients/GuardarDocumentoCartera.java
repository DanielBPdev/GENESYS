package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.DocumentoCarteraModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarDocumentoCartera
 */
public class GuardarDocumentoCartera extends ServiceClient { 
    	private DocumentoCarteraModeloDTO documentoCarteraDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DocumentoCarteraModeloDTO result;
  
 	public GuardarDocumentoCartera (DocumentoCarteraModeloDTO documentoCarteraDTO){
 		super();
		this.documentoCarteraDTO=documentoCarteraDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(documentoCarteraDTO == null ? null : Entity.json(documentoCarteraDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (DocumentoCarteraModeloDTO) response.readEntity(DocumentoCarteraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DocumentoCarteraModeloDTO getResult() {
		return result;
	}

 
  
  	public void setDocumentoCarteraDTO (DocumentoCarteraModeloDTO documentoCarteraDTO){
 		this.documentoCarteraDTO=documentoCarteraDTO;
 	}
 	
 	public DocumentoCarteraModeloDTO getDocumentoCarteraDTO (){
 		return documentoCarteraDTO;
 	}
  
}