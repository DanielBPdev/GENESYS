package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.modelo.DocumentoSoporteModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/registrarDocumentoSoporte
 */
public class RegistrarDocumentoSoporte extends ServiceClient { 
    	private DocumentoSoporteModeloDTO documentoSoporteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DocumentoSoporteModeloDTO result;
  
 	public RegistrarDocumentoSoporte (DocumentoSoporteModeloDTO documentoSoporteDTO){
 		super();
		this.documentoSoporteDTO=documentoSoporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(documentoSoporteDTO == null ? null : Entity.json(documentoSoporteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (DocumentoSoporteModeloDTO) response.readEntity(DocumentoSoporteModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DocumentoSoporteModeloDTO getResult() {
		return result;
	}

 
  
  	public void setDocumentoSoporteDTO (DocumentoSoporteModeloDTO documentoSoporteDTO){
 		this.documentoSoporteDTO=documentoSoporteDTO;
 	}
 	
 	public DocumentoSoporteModeloDTO getDocumentoSoporteDTO (){
 		return documentoSoporteDTO;
 	}
  
}