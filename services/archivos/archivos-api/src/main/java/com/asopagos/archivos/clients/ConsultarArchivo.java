package com.asopagos.archivos.clients;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/archivos/{identificadorArchivo}
 */
public class ConsultarArchivo extends ServiceClient {
 
  	private String identificadorArchivo;
  
  	private String versionDocumento;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private MultipartFormDataOutput result;
  
 	public ConsultarArchivo (String identificadorArchivo,String versionDocumento){
 		super();
		this.identificadorArchivo=identificadorArchivo;
		this.versionDocumento=versionDocumento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("identificadorArchivo", identificadorArchivo)
									.queryParam("versionDocumento", versionDocumento)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (MultipartFormDataOutput) response.readEntity(MultipartFormDataOutput.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public MultipartFormDataOutput getResult() {
		return result;
	}

 	public void setIdentificadorArchivo (String identificadorArchivo){
 		this.identificadorArchivo=identificadorArchivo;
 	}
 	
 	public String getIdentificadorArchivo (){
 		return identificadorArchivo;
 	}
  
  	public void setVersionDocumento (String versionDocumento){
 		this.versionDocumento=versionDocumento;
 	}
 	
 	public String getVersionDocumento (){
 		return versionDocumento;
 	}
  
}