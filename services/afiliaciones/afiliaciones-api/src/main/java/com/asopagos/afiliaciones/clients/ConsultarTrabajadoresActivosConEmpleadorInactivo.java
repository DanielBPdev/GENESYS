package com.asopagos.afiliaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.afiliaciones.dto.RelacionTrabajadorEmpresaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/consultarTrabajadoresActivosConEmpleadorInactivo
 */
public class ConsultarTrabajadoresActivosConEmpleadorInactivo extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RelacionTrabajadorEmpresaDTO> result;
  
 	public ConsultarTrabajadoresActivosConEmpleadorInactivo (){
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
		this.result = (List<RelacionTrabajadorEmpresaDTO>) response.readEntity(new GenericType<List<RelacionTrabajadorEmpresaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RelacionTrabajadorEmpresaDTO> getResult() {
		return result;
	}

 
  
}