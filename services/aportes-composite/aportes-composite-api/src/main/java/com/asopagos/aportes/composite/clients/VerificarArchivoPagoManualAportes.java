package com.asopagos.aportes.composite.clients;

import java.lang.String;
import com.asopagos.dto.aportes.ResultadoArchivoAporteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/verificarArchivoPagoManualAportes
 */
public class VerificarArchivoPagoManualAportes extends ServiceClient { 
   	private String identificadorDocumento;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoArchivoAporteDTO result;
  
 	public VerificarArchivoPagoManualAportes (String identificadorDocumento){
 		super();
		this.identificadorDocumento=identificadorDocumento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("identificadorDocumento", identificadorDocumento)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoArchivoAporteDTO) response.readEntity(ResultadoArchivoAporteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoArchivoAporteDTO getResult() {
		return result;
	}

 
  	public void setIdentificadorDocumento (String identificadorDocumento){
 		this.identificadorDocumento=identificadorDocumento;
 	}
 	
 	public String getIdentificadorDocumento (){
 		return identificadorDocumento;
 	}
  
  
}