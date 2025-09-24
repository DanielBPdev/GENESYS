package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/registrarArchivoTerceroPagadorEfectivo
 */
public class RegistrarArchivoTerceroPagadorEfectivo extends ServiceClient {
 
  
  	private String nombreDocumento;
  	private String idDocumento;
  	private String nombreUsuario;
  	private String versionDocumento;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RegistrarArchivoTerceroPagadorEfectivo (String nombreDocumento,String idDocumento,String nombreUsuario,String versionDocumento){
 		super();
		this.nombreDocumento=nombreDocumento;
		this.idDocumento=idDocumento;
		this.nombreUsuario=nombreUsuario;
		this.versionDocumento=versionDocumento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nombreDocumento", nombreDocumento)
						.queryParam("idDocumento", idDocumento)
						.queryParam("nombreUsuario", nombreUsuario)
						.queryParam("versionDocumento", versionDocumento)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Long getResult() {
		return result;
	}

 
  	public void setNombreDocumento (String nombreDocumento){
 		this.nombreDocumento=nombreDocumento;
 	}
 	
 	public String getNombreDocumento (){
 		return nombreDocumento;
 	}
  	public void setIdDocumento (String idDocumento){
 		this.idDocumento=idDocumento;
 	}
 	
 	public String getIdDocumento (){
 		return idDocumento;
 	}
  	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  	public void setVersionDocumento (String versionDocumento){
 		this.versionDocumento=versionDocumento;
 	}
 	
 	public String getVersionDocumento (){
 		return versionDocumento;
 	}
  
}