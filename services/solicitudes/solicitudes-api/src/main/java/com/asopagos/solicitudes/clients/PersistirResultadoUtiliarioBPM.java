package com.asopagos.solicitudes.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudes/persistirResultadoUtiliarioBPM
 */
public class PersistirResultadoUtiliarioBPM extends ServiceClient { 
   	private String resultado;
  	private String usuario;
  	private String numeroradicado;
   
  
 	public PersistirResultadoUtiliarioBPM (String resultado,String usuario,String numeroradicado){
 		super();
		this.resultado=resultado;
		this.usuario=usuario;
		this.numeroradicado=numeroradicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("resultado", resultado)
			.queryParam("usuario", usuario)
			.queryParam("numeroradicado", numeroradicado)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setResultado (String resultado){
 		this.resultado=resultado;
 	}
 	
 	public String getResultado (){
 		return resultado;
 	}
  	public void setUsuario (String usuario){
 		this.usuario=usuario;
 	}
 	
 	public String getUsuario (){
 		return usuario;
 	}
  	public void setNumeroradicado (String numeroradicado){
 		this.numeroradicado=numeroradicado;
 	}
 	
 	public String getNumeroradicado (){
 		return numeroradicado;
 	}
  
  
}