package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.Response;
import com.asopagos.subsidiomonetario.dto.ExportarInconsistenciasDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/exportarRegistrosInconsistentesBloqueo
 */
public class ExportarRegistrosInconsistentesBloqueo extends ServiceClient { 
    	private ExportarInconsistenciasDTO datosExportar;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Response result;
  
 	public ExportarRegistrosInconsistentesBloqueo (ExportarInconsistenciasDTO datosExportar){
 		super();
		this.datosExportar=datosExportar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosExportar == null ? null : Entity.json(datosExportar));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Response) response.readEntity(Response.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Response getResult() {
		return result;
	}

 
  
  	public void setDatosExportar (ExportarInconsistenciasDTO datosExportar){
 		this.datosExportar=datosExportar;
 	}
 	
 	public ExportarInconsistenciasDTO getDatosExportar (){
 		return datosExportar;
 	}
  
}