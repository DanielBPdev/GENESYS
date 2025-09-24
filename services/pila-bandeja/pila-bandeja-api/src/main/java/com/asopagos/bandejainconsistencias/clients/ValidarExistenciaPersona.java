package com.asopagos.bandejainconsistencias.clients;

import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pilaBandeja/validarExistenciaPersona
 */
public class ValidarExistenciaPersona extends ServiceClient {
 
  
  	private String numId;
  	private TipoIdentificacionEnum TipoIdentificacionEnum;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ValidarExistenciaPersona (String numId,TipoIdentificacionEnum TipoIdentificacionEnum){
 		super();
		this.numId=numId;
		this.TipoIdentificacionEnum=TipoIdentificacionEnum;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numId", numId)
						.queryParam("TipoIdentificacionEnum", TipoIdentificacionEnum)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Boolean getResult() {
		return result;
	}

 
  	public void setNumId (String numId){
 		this.numId=numId;
 	}
 	
 	public String getNumId (){
 		return numId;
 	}
  	public void setTipoIdentificacionEnum (TipoIdentificacionEnum TipoIdentificacionEnum){
 		this.TipoIdentificacionEnum=TipoIdentificacionEnum;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionEnum (){
 		return TipoIdentificacionEnum;
 	}
  
}