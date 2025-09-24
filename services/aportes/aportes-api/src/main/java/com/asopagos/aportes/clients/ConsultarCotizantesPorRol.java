package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.aportes.CotizanteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarCotizantesPorRol
 */
public class ConsultarCotizantesPorRol extends ServiceClient { 
    	private List<Long> idRoles;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CotizanteDTO> result;
  
 	public ConsultarCotizantesPorRol (List<Long> idRoles){
 		super();
		this.idRoles=idRoles;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idRoles == null ? null : Entity.json(idRoles));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<CotizanteDTO>) response.readEntity(new GenericType<List<CotizanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<CotizanteDTO> getResult() {
		return result;
	}

 
  
  	public void setIdRoles (List<Long> idRoles){
 		this.idRoles=idRoles;
 	}
 	
 	public List<Long> getIdRoles (){
 		return idRoles;
 	}
  
}