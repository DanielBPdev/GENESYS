package com.asopagos.fovis.composite.clients;

import com.asopagos.dto.CruceDetalleDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovisComposite/consolidarCruce
 */
public class ConsolidarCruce extends ServiceClient { 
   	private Long idCargue;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CruceDetalleDTO> result;
  
 	public ConsolidarCruce (Long idCargue){
 		super();
		this.idCargue=idCargue;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idCargue", idCargue)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<CruceDetalleDTO>) response.readEntity(new GenericType<List<CruceDetalleDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<CruceDetalleDTO> getResult() {
		return result;
	}

 
  	public void setIdCargue (Long idCargue){
 		this.idCargue=idCargue;
 	}
 	
 	public Long getIdCargue (){
 		return idCargue;
 	}
  
  
}