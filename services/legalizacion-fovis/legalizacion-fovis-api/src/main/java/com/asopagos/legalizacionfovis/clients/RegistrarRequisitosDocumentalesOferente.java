package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.fovis.DocumentoSoporteOferenteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/registrarRequisitosDocumentalesOferente
 */
public class RegistrarRequisitosDocumentalesOferente extends ServiceClient { 
    	private DocumentoSoporteOferenteDTO documentoSoporteOferenteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DocumentoSoporteOferenteDTO result;
  
 	public RegistrarRequisitosDocumentalesOferente (DocumentoSoporteOferenteDTO documentoSoporteOferenteDTO){
 		super();
		this.documentoSoporteOferenteDTO=documentoSoporteOferenteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(documentoSoporteOferenteDTO == null ? null : Entity.json(documentoSoporteOferenteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (DocumentoSoporteOferenteDTO) response.readEntity(DocumentoSoporteOferenteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DocumentoSoporteOferenteDTO getResult() {
		return result;
	}

 
  
  	public void setDocumentoSoporteOferenteDTO (DocumentoSoporteOferenteDTO documentoSoporteOferenteDTO){
 		this.documentoSoporteOferenteDTO=documentoSoporteOferenteDTO;
 	}
 	
 	public DocumentoSoporteOferenteDTO getDocumentoSoporteOferenteDTO (){
 		return documentoSoporteOferenteDTO;
 	}
  
}