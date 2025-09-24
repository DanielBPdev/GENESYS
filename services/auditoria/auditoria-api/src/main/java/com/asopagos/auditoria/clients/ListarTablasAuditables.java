package com.asopagos.auditoria.clients;

import java.util.Set;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/auditoria/listadoTablasAuditables
 */
public class ListarTablasAuditables extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Set<String> result;
  
 	public ListarTablasAuditables (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Set<String>) response.readEntity(Set.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Set<String> getResult() {
		return result;
	}

 
  
}