package com.asopagos.reportes.clients;

import com.asopagos.enumeraciones.reportes.ReporteGiassEnum;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/reportesGiass/generarReporteTabla/{tablaGiass}
 */
public class GenerarReporteTabla extends ServiceClient {
 
  	private ReporteGiassEnum tablaGiass;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Response result;
  
 	public GenerarReporteTabla (ReporteGiassEnum tablaGiass){
 		super();
		this.tablaGiass=tablaGiass;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("tablaGiass", tablaGiass)
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

 	public void setTablaGiass (ReporteGiassEnum tablaGiass){
 		this.tablaGiass=tablaGiass;
 	}
 	
 	public ReporteGiassEnum getTablaGiass (){
 		return tablaGiass;
 	}
  
  
}