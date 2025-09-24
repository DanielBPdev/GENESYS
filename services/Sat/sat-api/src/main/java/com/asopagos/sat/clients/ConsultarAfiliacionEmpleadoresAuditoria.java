package com.asopagos.sat.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.sat.dto.AfiliacionEmpleadoresDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/sat/consultarAfiliacionEmpleadoresAud
 */
public class ConsultarAfiliacionEmpleadoresAuditoria extends ServiceClient {
 
  
  	private Long id;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AfiliacionEmpleadoresDTO> result;
  
 	public ConsultarAfiliacionEmpleadoresAuditoria (Long id){
 		super();
		this.id=id;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("id", id)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<AfiliacionEmpleadoresDTO>) response.readEntity(new GenericType<List<AfiliacionEmpleadoresDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<AfiliacionEmpleadoresDTO> getResult() {
		return result;
	}

 
  	public void setId (Long id){
 		this.id=id;
 	}
 	
 	public Long getId (){
 		return id;
 	}
  
}