package com.asopagos.entidaddescuento.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/entidadDescuento/archivoDescuentos/obtenerTodos
 */
public class ObtenerArchivosDescuento extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ArchivoEntidadDescuentoDTO> result;
  
 	public ObtenerArchivosDescuento (){
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
		this.result = (List<ArchivoEntidadDescuentoDTO>) response.readEntity(new GenericType<List<ArchivoEntidadDescuentoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ArchivoEntidadDescuentoDTO> getResult() {
		return result;
	}

 
  
}