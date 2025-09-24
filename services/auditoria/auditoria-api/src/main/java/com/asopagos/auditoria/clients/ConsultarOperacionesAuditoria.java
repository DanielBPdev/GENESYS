package com.asopagos.auditoria.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.auditoria.dto.ParametrizacionTablaAuditableDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/auditoria/consultarOperacionesAuditoria
 */
public class ConsultarOperacionesAuditoria extends ServiceClient {
 
  
  	private List<String> tablas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ParametrizacionTablaAuditableDTO> result;
  
 	public ConsultarOperacionesAuditoria (List<String> tablas){
 		super();
		this.tablas=tablas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tablas", tablas.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ParametrizacionTablaAuditableDTO>) response.readEntity(new GenericType<List<ParametrizacionTablaAuditableDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ParametrizacionTablaAuditableDTO> getResult() {
		return result;
	}

 
  	public void setTablas (List<String> tablas){
 		this.tablas=tablas;
 	}
 	
 	public List<String> getTablas (){
 		return tablas;
 	}
  
}