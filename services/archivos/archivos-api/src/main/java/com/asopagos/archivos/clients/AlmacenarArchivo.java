package com.asopagos.archivos.clients;

import com.asopagos.dto.InformacionArchivoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivos/almacenarArchivo
 */
public class AlmacenarArchivo extends ServiceClient { 
    	private InformacionArchivoDTO infoFile;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InformacionArchivoDTO result;
  
 	public AlmacenarArchivo (InformacionArchivoDTO infoFile){
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
		result = (InformacionArchivoDTO) response.readEntity(InformacionArchivoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public InformacionArchivoDTO getResult() {
		return result;
	}

 
  
  	public void setInfoFile (InformacionArchivoDTO infoFile){
 		this.infoFile=infoFile;
 	}
 	
 	public InformacionArchivoDTO getInfoFile (){
 		return infoFile;
 	}
  
}