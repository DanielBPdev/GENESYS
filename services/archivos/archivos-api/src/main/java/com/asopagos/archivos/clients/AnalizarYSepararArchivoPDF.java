package com.asopagos.archivos.clients;

import com.asopagos.dto.InformacionArchivoClasificacionDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivos/analizarPDF
 */
public class AnalizarYSepararArchivoPDF extends ServiceClient { 
    	private InformacionArchivoDTO infoFile;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InformacionArchivoClasificacionDTO result;
  
 	public AnalizarYSepararArchivoPDF (InformacionArchivoDTO infoFile){
 		super();
		this.infoFile=infoFile;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(infoFile == null ? null : Entity.json(infoFile));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (InformacionArchivoClasificacionDTO) response.readEntity(InformacionArchivoClasificacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public InformacionArchivoClasificacionDTO getResult() {
		return result;
	}

 
  
  	public void setInfoFile (InformacionArchivoDTO infoFile){
 		this.infoFile=infoFile;
 	}
 	
 	public InformacionArchivoDTO getInfoFile (){
 		return infoFile;
 	}
  
}