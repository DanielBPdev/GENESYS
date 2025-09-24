package com.asopagos.afiliaciones.personas.web.clients;

import com.asopagos.novedades.dto.RespuestaValidacionArchivoDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliacionesPersonasWebMultiple/validacionesArchivoAfiliaciones
 */
public class ValidacionesArchivoAfiliaciones extends ServiceClient {
 
  
  	private String idValidacion;
  	private String valorCampo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaValidacionArchivoDTO result;
  
 	public ValidacionesArchivoAfiliaciones (String idValidacion,String valorCampo){
 		super();
		this.idValidacion=idValidacion;
		this.valorCampo=valorCampo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idValidacion", idValidacion)
						.queryParam("valorCampo", valorCampo)
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

 
  	public void setIdValidacion (String idValidacion){
 		this.idValidacion=idValidacion;
 	}
 	
 	public String getIdValidacion (){
 		return idValidacion;
 	}
  	public void setValorCampo (String valorCampo){
 		this.valorCampo=valorCampo;
 	}
 	
 	public String getValorCampo (){
 		return valorCampo;
 	}
  
}