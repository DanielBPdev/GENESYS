package com.asopagos.subsidiomonetario.clients;

import com.asopagos.dto.subsidiomonetario.liquidacion.CargueArchivoBloqueoCMDTO;
import com.asopagos.subsidiomonetario.dto.ResultadoValidacionArchivoBloqueoCMDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/verificarEstructuraArchivoBloquedoCM
 */
public class VerificarEstructuraArchivoBloquedoCM extends ServiceClient { 
    	private CargueArchivoBloqueoCMDTO cargue;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoBloqueoCMDTO result;
  
 	public VerificarEstructuraArchivoBloquedoCM (CargueArchivoBloqueoCMDTO cargue){
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
		result = (ResultadoValidacionArchivoBloqueoCMDTO) response.readEntity(ResultadoValidacionArchivoBloqueoCMDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoValidacionArchivoBloqueoCMDTO getResult() {
		return result;
	}

 
  
  	public void setCargue (CargueArchivoBloqueoCMDTO cargue){
 		this.cargue=cargue;
 	}
 	
 	public CargueArchivoBloqueoCMDTO getCargue (){
 		return cargue;
 	}
  
}