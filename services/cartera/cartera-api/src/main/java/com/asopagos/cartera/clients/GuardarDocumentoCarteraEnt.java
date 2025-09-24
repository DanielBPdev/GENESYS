package com.asopagos.cartera.clients;

import com.asopagos.entidades.ccf.cartera.DocumentoCartera;
import com.asopagos.dto.modelo.DocumentoCarteraModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarDocumentoCarteraEnt
 */
public class GuardarDocumentoCarteraEnt extends ServiceClient { 
    	private DocumentoCarteraModeloDTO documentoCarteraDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DocumentoCartera result;
  
 	public GuardarDocumentoCarteraEnt (DocumentoCarteraModeloDTO documentoCarteraDTO){
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
		result = (DocumentoCartera) response.readEntity(DocumentoCartera.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DocumentoCartera getResult() {
		return result;
	}

 
  
  	public void setDocumentoCarteraDTO (DocumentoCarteraModeloDTO documentoCarteraDTO){
 		this.documentoCarteraDTO=documentoCarteraDTO;
 	}
 	
 	public DocumentoCarteraModeloDTO getDocumentoCarteraDTO (){
 		return documentoCarteraDTO;
 	}
  
}