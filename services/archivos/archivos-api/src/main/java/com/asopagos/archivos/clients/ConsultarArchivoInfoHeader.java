package com.asopagos.archivos.clients;

import javax.ws.rs.core.Response;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/archivos/infoHeader/{identificadorArchivo}
 */
public class ConsultarArchivoInfoHeader extends ServiceClient {
 
  	private String identificadorArchivo;
  
  	private boolean toDownload;
  	private String versionDocumento;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Response result;
  
 	public ConsultarArchivoInfoHeader (String identificadorArchivo,boolean toDownload,String versionDocumento){
 		super();
		this.identificadorArchivo=identificadorArchivo;
		this.toDownload=toDownload;
		this.versionDocumento=versionDocumento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("identificadorArchivo", identificadorArchivo)
									.queryParam("toDownload", toDownload)
						.queryParam("versionDocumento", versionDocumento)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Response) response.readEntity(Response.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Response getResult() {
		return result;
	}

 	public void setIdentificadorArchivo (String identificadorArchivo){
 		this.identificadorArchivo=identificadorArchivo;
 	}
 	
 	public String getIdentificadorArchivo (){
 		return identificadorArchivo;
 	}
  
  	public void setToDownload (boolean toDownload){
 		this.toDownload=toDownload;
 	}
 	
 	public boolean getToDownload (){
 		return toDownload;
 	}
  	public void setVersionDocumento (String versionDocumento){
 		this.versionDocumento=versionDocumento;
 	}
 	
 	public String getVersionDocumento (){
 		return versionDocumento;
 	}
  
}