package com.asopagos.reportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.reportes.dto.GeneracionReporteNormativoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/reportesNormativos/consultarHistoricosReportesOficiales
 */
public class ConsultarHistoricosReportesOficiales extends ServiceClient { 
    	private GeneracionReporteNormativoDTO generacionReporteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<GeneracionReporteNormativoDTO> result;
  
 	public ConsultarHistoricosReportesOficiales (GeneracionReporteNormativoDTO generacionReporteDTO){
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
		result = (List<GeneracionReporteNormativoDTO>) response.readEntity(new GenericType<List<GeneracionReporteNormativoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<GeneracionReporteNormativoDTO> getResult() {
		return result;
	}

 
  
  	public void setGeneracionReporteDTO (GeneracionReporteNormativoDTO generacionReporteDTO){
 		this.generacionReporteDTO=generacionReporteDTO;
 	}
 	
 	public GeneracionReporteNormativoDTO getGeneracionReporteDTO (){
 		return generacionReporteDTO;
 	}
  
}