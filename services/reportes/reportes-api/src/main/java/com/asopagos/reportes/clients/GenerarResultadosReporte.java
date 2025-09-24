package com.asopagos.reportes.clients;

import com.asopagos.reportes.dto.ResultadoReporteDTO;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/reportesNormativos/generarResultadosReporte
 */
public class GenerarResultadosReporte extends ServiceClient { 
    	private GeneracionReporteNormativoDTO generacionReporteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoReporteDTO result;
  
 	public GenerarResultadosReporte (GeneracionReporteNormativoDTO generacionReporteDTO){
 		super();
		this.generacionReporteDTO=generacionReporteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(generacionReporteDTO == null ? null : Entity.json(generacionReporteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoReporteDTO) response.readEntity(ResultadoReporteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoReporteDTO getResult() {
		return result;
	}

 
  
  	public void setGeneracionReporteDTO (GeneracionReporteNormativoDTO generacionReporteDTO){
 		this.generacionReporteDTO=generacionReporteDTO;
 	}
 	
 	public GeneracionReporteNormativoDTO getGeneracionReporteDTO (){
 		return generacionReporteDTO;
 	}
  
}