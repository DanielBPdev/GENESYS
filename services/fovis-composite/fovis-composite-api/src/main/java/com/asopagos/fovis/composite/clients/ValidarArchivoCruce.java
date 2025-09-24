package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.CargueArchivoCruceFovisDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/validarArchivoCruce
 */
public class ValidarArchivoCruce extends ServiceClient { 
    	private CargueArchivoCruceFovisDTO cargue;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoDTO result;
  
 	public ValidarArchivoCruce (CargueArchivoCruceFovisDTO cargue){
 		super();
		this.cargue=cargue;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(cargue == null ? null : Entity.json(cargue));
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

 
  
  	public void setCargue (CargueArchivoCruceFovisDTO cargue){
 		this.cargue=cargue;
 	}
 	
 	public CargueArchivoCruceFovisDTO getCargue (){
 		return cargue;
 	}
  
}