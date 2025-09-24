package com.asopagos.novedades.clients;

import javax.ws.rs.core.Response;
import com.asopagos.novedades.dto.GenerarReporteSupervivenciaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/generarReporteAfiSupervivencia
 */
public class GenerarReporteAfiliadosSupervivencia extends ServiceClient { 
    	private GenerarReporteSupervivenciaDTO gerarReporte;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Response result;
  
 	public GenerarReporteAfiliadosSupervivencia (GenerarReporteSupervivenciaDTO gerarReporte){
 		super();
		this.gerarReporte=gerarReporte;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(gerarReporte == null ? null : Entity.json(gerarReporte));
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

 
  
  	public void setGerarReporte (GenerarReporteSupervivenciaDTO gerarReporte){
 		this.gerarReporte=gerarReporte;
 	}
 	
 	public GenerarReporteSupervivenciaDTO getGerarReporte (){
 		return gerarReporte;
 	}
  
}