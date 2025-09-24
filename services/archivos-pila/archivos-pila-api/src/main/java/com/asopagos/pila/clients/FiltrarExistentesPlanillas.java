package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.ArchivoPilaDTO;
import java.lang.Integer;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/filtrarExistentesPlanillas
 */
public class FiltrarExistentesPlanillas extends ServiceClient { 
   	private Integer tipoArchivos;
   	private List<ArchivoPilaDTO> archivosFTP;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ArchivoPilaDTO> result;
  
 	public FiltrarExistentesPlanillas (Integer tipoArchivos,List<ArchivoPilaDTO> archivosFTP){
 		super();
		this.tipoArchivos=tipoArchivos;
		this.archivosFTP=archivosFTP;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoArchivos", tipoArchivos)
			.request(MediaType.APPLICATION_JSON)
			.post(archivosFTP == null ? null : Entity.json(archivosFTP));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ArchivoPilaDTO>) response.readEntity(new GenericType<List<ArchivoPilaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ArchivoPilaDTO> getResult() {
		return result;
	}

 
  	public void setTipoArchivos (Integer tipoArchivos){
 		this.tipoArchivos=tipoArchivos;
 	}
 	
 	public Integer getTipoArchivos (){
 		return tipoArchivos;
 	}
  
  	public void setArchivosFTP (List<ArchivoPilaDTO> archivosFTP){
 		this.archivosFTP=archivosFTP;
 	}
 	
 	public List<ArchivoPilaDTO> getArchivosFTP (){
 		return archivosFTP;
 	}
  
}