package com.asopagos.afiliaciones.personas.web.composite.clients;

import java.lang.Long;
import java.util.Map;
import java.lang.Object;
import java.lang.String;
import java.lang.Integer;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliacionesPersonasWeb/radicarSolicitud/{idSolicitud}
 */
public class RadicarSolicitudAfiliacionWeb extends ServiceClient { 
  	private Long idSolicitud;
   	private Integer resultadoFormulario;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public RadicarSolicitudAfiliacionWeb (Long idSolicitud,Integer resultadoFormulario){
 		super();
		this.idSolicitud=idSolicitud;
		this.resultadoFormulario=resultadoFormulario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idSolicitud", idSolicitud)
			.queryParam("resultadoFormulario", resultadoFormulario)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,Object>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,Object> getResult() {
		return result;
	}

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  	public void setResultadoFormulario (Integer resultadoFormulario){
 		this.resultadoFormulario=resultadoFormulario;
 	}
 	
 	public Integer getResultadoFormulario (){
 		return resultadoFormulario;
 	}
  
  
}