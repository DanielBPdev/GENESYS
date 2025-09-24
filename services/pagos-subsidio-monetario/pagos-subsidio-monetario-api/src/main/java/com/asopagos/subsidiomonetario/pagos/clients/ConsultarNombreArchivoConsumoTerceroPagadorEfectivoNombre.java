package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarNombreArchivoConsumoTerceroPagadorEfectivoNombre/{nombreArchivo}
 */
public class ConsultarNombreArchivoConsumoTerceroPagadorEfectivoNombre extends ServiceClient {
 
  	private String nombreArchivo;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ConsultarNombreArchivoConsumoTerceroPagadorEfectivoNombre (String nombreArchivo){
 		super();
		this.nombreArchivo=nombreArchivo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("nombreArchivo", nombreArchivo)
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

 	public void setNombreArchivo (String nombreArchivo){
 		this.nombreArchivo=nombreArchivo;
 	}
 	
 	public String getNombreArchivo (){
 		return nombreArchivo;
 	}
  
  
}