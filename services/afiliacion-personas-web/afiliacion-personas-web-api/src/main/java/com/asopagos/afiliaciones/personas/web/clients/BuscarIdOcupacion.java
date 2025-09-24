package com.asopagos.afiliaciones.personas.web.clients;

import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliacionesPersonasWebMultiple/buscarIdOcupacion
 */
public class BuscarIdOcupacion extends ServiceClient {
 
  
  	private String nombreOcupacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaValidacionArchivoDTO result;
  
 	public BuscarIdOcupacion (String nombreOcupacion){
 		super();
		this.nombreOcupacion=nombreOcupacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nombreOcupacion", nombreOcupacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RespuestaValidacionArchivoDTO) response.readEntity(RespuestaValidacionArchivoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RespuestaValidacionArchivoDTO getResult() {
		return result;
	}

 
  	public void setNombreOcupacion (String nombreOcupacion){
 		this.nombreOcupacion=nombreOcupacion;
 	}
 	
 	public String getNombreOcupacion (){
 		return nombreOcupacion;
 	}
  
}