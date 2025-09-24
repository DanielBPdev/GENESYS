package com.asopagos.legalizacionfovis.clients;

import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/existeLicenciaMatricula
 */
public class ExisteLicenciaMatricula extends ServiceClient {
 
  
  	private String matriculaInmobiliaria;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ExisteLicenciaMatricula (String matriculaInmobiliaria){
 		super();
		this.matriculaInmobiliaria=matriculaInmobiliaria;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("matriculaInmobiliaria", matriculaInmobiliaria)
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

 
  	public void setMatriculaInmobiliaria (String matriculaInmobiliaria){
 		this.matriculaInmobiliaria=matriculaInmobiliaria;
 	}
 	
 	public String getMatriculaInmobiliaria (){
 		return matriculaInmobiliaria;
 	}
  
}