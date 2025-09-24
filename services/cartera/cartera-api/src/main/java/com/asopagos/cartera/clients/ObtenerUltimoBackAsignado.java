package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/obtenerUltimoBackAsignado
 */
public class ObtenerUltimoBackAsignado extends ServiceClient {
 
  
  	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private TipoTransaccionEnum tipoTransaccion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ObtenerUltimoBackAsignado (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,TipoTransaccionEnum tipoTransaccion){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.tipoTransaccion=tipoTransaccion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoSolicitante", tipoSolicitante)
						.queryParam("tipoTransaccion", tipoTransaccion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public String getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  	public void setTipoTransaccion (TipoTransaccionEnum tipoTransaccion){
 		this.tipoTransaccion=tipoTransaccion;
 	}
 	
 	public TipoTransaccionEnum getTipoTransaccion (){
 		return tipoTransaccion;
 	}
  
}