package com.asopagos.afiliaciones.personas.web.clients;

import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliacionesPersonasWebMultiple/buscarCodigoPais
 */
public class BuscarCodigoPais extends ServiceClient {
 
  
  	private String nombrePais;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaValidacionArchivoDTO result;
  
 	public BuscarCodigoPais (String nombrePais){
 		super();
		this.nombrePais=nombrePais;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nombrePais", nombrePais)
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

 
  	public void setNombrePais (String nombrePais){
 		this.nombrePais=nombrePais;
 	}
 	
 	public String getNombrePais (){
 		return nombrePais;
 	}
  
}