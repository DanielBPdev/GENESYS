package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.fovis.DocumentoSoporteProveedorDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/registrarRequisitosDocumentalesProveedor
 */
public class RegistrarRequisitosDocumentalesProveedor extends ServiceClient { 
    	private DocumentoSoporteProveedorDTO documentoSoporteProveedorDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DocumentoSoporteProveedorDTO result;
  
 	public RegistrarRequisitosDocumentalesProveedor (DocumentoSoporteProveedorDTO documentoSoporteProveedorDTO){
 		super();
		this.documentoSoporteProveedorDTO=documentoSoporteProveedorDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(documentoSoporteProveedorDTO == null ? null : Entity.json(documentoSoporteProveedorDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (DocumentoSoporteProveedorDTO) response.readEntity(DocumentoSoporteProveedorDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DocumentoSoporteProveedorDTO getResult() {
		return result;
	}

 
  
  	public void setDocumentoSoporteProveedorDTO (DocumentoSoporteProveedorDTO documentoSoporteProveedorDTO){
 		this.documentoSoporteProveedorDTO=documentoSoporteProveedorDTO;
 	}
 	
 	public DocumentoSoporteProveedorDTO getDocumentoSoporteProveedorDTO (){
 		return documentoSoporteProveedorDTO;
 	}
  
}