package com.asopagos.novedades.clients;

import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoGestionUsuariosDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesCargueMultiple/verificarEstructuraArchivoCcf
 */
public class VerificarEstructuraArchivoCcf extends ServiceClient { 
    	
    private InformacionArchivoDTO archivo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoGestionUsuariosDTO result;
  
 	public VerificarEstructuraArchivoCcf (InformacionArchivoDTO archivo){
 		super();
		this.archivo=archivo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(archivo == null ? null : Entity.json(archivo));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoValidacionArchivoGestionUsuariosDTO) response.readEntity(ResultadoValidacionArchivoGestionUsuariosDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoValidacionArchivoGestionUsuariosDTO getResult() {
		return result;
	}

 
  
  	public void setArchivo (InformacionArchivoDTO archivo){
 		this.archivo=archivo;
 	}
 	
 	public InformacionArchivoDTO getArchivo (){
 		return archivo;
 	}
  
}