package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.enumeraciones.subsidiomonetario.pagos.anibol.EstadoArchivoConsumoTerceroPagadorEfectivo;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/actualizarEstadoProcesadoArchivoTerceroPagadorEfectivo
 */
public class ActualizarEstadoProcesadoArchivoTerceroPagadorEfectivo extends ServiceClient { 
   	private Long archivoTerceroPagadorEfectivo;
  	private EstadoArchivoConsumoTerceroPagadorEfectivo estado;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public ActualizarEstadoProcesadoArchivoTerceroPagadorEfectivo (Long archivoTerceroPagadorEfectivo,EstadoArchivoConsumoTerceroPagadorEfectivo estado){
 		super();
		this.archivoTerceroPagadorEfectivo=archivoTerceroPagadorEfectivo;
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("archivoTerceroPagadorEfectivo", archivoTerceroPagadorEfectivo)
			.queryParam("estado", estado)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  	public void setArchivoTerceroPagadorEfectivo (Long archivoTerceroPagadorEfectivo){
 		this.archivoTerceroPagadorEfectivo=archivoTerceroPagadorEfectivo;
 	}
 	
 	public Long getArchivoTerceroPagadorEfectivo (){
 		return archivoTerceroPagadorEfectivo;
 	}
  	public void setEstado (EstadoArchivoConsumoTerceroPagadorEfectivo estado){
 		this.estado=estado;
 	}
 	
 	public EstadoArchivoConsumoTerceroPagadorEfectivo getEstado (){
 		return estado;
 	}
  
  
}