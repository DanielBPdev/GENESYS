package com.asopagos.novedades.clients;

import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.novedades.dto.ArchivoSupervivenciaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesCargueMultiple/verificarArchivoSupervivencia
 */
public class VerificarArchivoSupervivencia extends ServiceClient { 
    	private ArchivoSupervivenciaDTO archivoSuperVivenciaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoDTO result;
  
 	public VerificarArchivoSupervivencia (ArchivoSupervivenciaDTO archivoSuperVivenciaDTO){
 		super();
		this.archivoSuperVivenciaDTO=archivoSuperVivenciaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(archivoSuperVivenciaDTO == null ? null : Entity.json(archivoSuperVivenciaDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoValidacionArchivoDTO) response.readEntity(ResultadoValidacionArchivoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoValidacionArchivoDTO getResult() {
		return result;
	}

 
  
  	public void setArchivoSuperVivenciaDTO (ArchivoSupervivenciaDTO archivoSuperVivenciaDTO){
 		this.archivoSuperVivenciaDTO=archivoSuperVivenciaDTO;
 	}
 	
 	public ArchivoSupervivenciaDTO getArchivoSuperVivenciaDTO (){
 		return archivoSuperVivenciaDTO;
 	}
  
}