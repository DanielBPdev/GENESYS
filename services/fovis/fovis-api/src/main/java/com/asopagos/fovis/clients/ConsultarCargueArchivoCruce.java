package com.asopagos.fovis.clients;

import com.asopagos.dto.CargueArchivoCruceFovisDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisCargue/consultarCargueArchivoCruce
 */
public class ConsultarCargueArchivoCruce extends ServiceClient {
 
  
  	private Long idCargue;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CargueArchivoCruceFovisDTO result;
  
 	public ConsultarCargueArchivoCruce (Long idCargue){
 		super();
		this.idCargue=idCargue;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCargue", idCargue)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CargueArchivoCruceFovisDTO) response.readEntity(CargueArchivoCruceFovisDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CargueArchivoCruceFovisDTO getResult() {
		return result;
	}

 
  	public void setIdCargue (Long idCargue){
 		this.idCargue=idCargue;
 	}
 	
 	public Long getIdCargue (){
 		return idCargue;
 	}
  
}