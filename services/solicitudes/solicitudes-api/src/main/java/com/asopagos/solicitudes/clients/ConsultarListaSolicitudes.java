package com.asopagos.solicitudes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.Solicitud360DTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudes/consultarListaSolicitudes
 */
public class ConsultarListaSolicitudes extends ServiceClient { 
    	private Solicitud360DTO filtros;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Solicitud360DTO> result;
  
 	public ConsultarListaSolicitudes (Solicitud360DTO filtros){
 		super();
		this.filtros=filtros;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(filtros == null ? null : Entity.json(filtros));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Solicitud360DTO>) response.readEntity(new GenericType<List<Solicitud360DTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Solicitud360DTO> getResult() {
		return result;
	}

 
  
  	public void setFiltros (Solicitud360DTO filtros){
 		this.filtros=filtros;
 	}
 	
 	public Solicitud360DTO getFiltros (){
 		return filtros;
 	}
  
}