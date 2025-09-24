package com.asopagos.reportes.clients;

import com.asopagos.enumeraciones.reportes.FormatoReporteEnum;
import javax.ws.rs.core.Response;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/reportesNormativos/exportar
 */
public class ExportarReporteNormativo extends ServiceClient { 
   	private FormatoReporteEnum formatoReporte;
   	private GeneracionReporteNormativoDTO generacionReporteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Response result;
  
 	public ExportarReporteNormativo (FormatoReporteEnum formatoReporte,GeneracionReporteNormativoDTO generacionReporteDTO){
 		super();
		this.formatoReporte=formatoReporte;
		this.generacionReporteDTO=generacionReporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("formatoReporte", formatoReporte)
			.request(MediaType.APPLICATION_JSON)
			.post(generacionReporteDTO == null ? null : Entity.json(generacionReporteDTO));
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

 
  	public void setFormatoReporte (FormatoReporteEnum formatoReporte){
 		this.formatoReporte=formatoReporte;
 	}
 	
 	public FormatoReporteEnum getFormatoReporte (){
 		return formatoReporte;
 	}
  
  	public void setGeneracionReporteDTO (GeneracionReporteNormativoDTO generacionReporteDTO){
 		this.generacionReporteDTO=generacionReporteDTO;
 	}
 	
 	public GeneracionReporteNormativoDTO getGeneracionReporteDTO (){
 		return generacionReporteDTO;
 	}
  
}