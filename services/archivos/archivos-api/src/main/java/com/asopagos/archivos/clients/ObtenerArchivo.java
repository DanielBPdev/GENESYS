package com.asopagos.archivos.clients;

import com.asopagos.dto.InformacionArchivoDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/archivos/obtenerArchivo/{identificadorArchivo}
 */
public class ObtenerArchivo extends ServiceClient {
 
  	private String identificadorArchivo;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InformacionArchivoDTO result;
  
 	public ObtenerArchivo (String identificadorArchivo){
 		super();
		this.identificadorArchivo=identificadorArchivo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("identificadorArchivo", identificadorArchivo)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InformacionArchivoDTO) response.readEntity(InformacionArchivoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InformacionArchivoDTO getResult() {
		return result;
	}

 	public void setIdentificadorArchivo (String identificadorArchivo){
 		this.identificadorArchivo=identificadorArchivo;
 	}
 	
 	public String getIdentificadorArchivo (){
 		return identificadorArchivo;
 	}
  
  
}