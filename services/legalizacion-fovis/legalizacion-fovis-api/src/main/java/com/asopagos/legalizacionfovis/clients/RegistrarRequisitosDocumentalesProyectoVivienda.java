package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.fovis.DocumentoSoporteProyectoViviendaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/legalizacionFovis/registrarRequisitosDocumentalesProyectoVivienda
 */
public class RegistrarRequisitosDocumentalesProyectoVivienda extends ServiceClient { 
    	private DocumentoSoporteProyectoViviendaDTO documentoSoporteProyectoViviendaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DocumentoSoporteProyectoViviendaDTO result;
  
 	public RegistrarRequisitosDocumentalesProyectoVivienda (DocumentoSoporteProyectoViviendaDTO documentoSoporteProyectoViviendaDTO){
 		super();
		this.documentoSoporteProyectoViviendaDTO=documentoSoporteProyectoViviendaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(documentoSoporteProyectoViviendaDTO == null ? null : Entity.json(documentoSoporteProyectoViviendaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (DocumentoSoporteProyectoViviendaDTO) response.readEntity(DocumentoSoporteProyectoViviendaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DocumentoSoporteProyectoViviendaDTO getResult() {
		return result;
	}

 
  
  	public void setDocumentoSoporteProyectoViviendaDTO (DocumentoSoporteProyectoViviendaDTO documentoSoporteProyectoViviendaDTO){
 		this.documentoSoporteProyectoViviendaDTO=documentoSoporteProyectoViviendaDTO;
 	}
 	
 	public DocumentoSoporteProyectoViviendaDTO getDocumentoSoporteProyectoViviendaDTO (){
 		return documentoSoporteProyectoViviendaDTO;
 	}
  
}