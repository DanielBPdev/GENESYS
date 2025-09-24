package com.asopagos.entidaddescuento.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.entidaddescuento.dto.ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/entidadDescuento/archivoDescuentos/obtenerTrazabilidad
 */
public class ObtenerInformacionTrazabilidad extends ServiceClient {
 
  
  	private List<String> nombresArchivos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO> result;
  
 	public ObtenerInformacionTrazabilidad (List<String> nombresArchivos){
 		super();
		this.nombresArchivos=nombresArchivos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nombresArchivos", nombresArchivos.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO>) response.readEntity(new GenericType<List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ArchivoEntidadDescuentoSubsidioPignoradoModeloDTO> getResult() {
		return result;
	}

 
  	public void setNombresArchivos (List<String> nombresArchivos){
 		this.nombresArchivos=nombresArchivos;
 	}
 	
 	public List<String> getNombresArchivos (){
 		return nombresArchivos;
 	}
  
}