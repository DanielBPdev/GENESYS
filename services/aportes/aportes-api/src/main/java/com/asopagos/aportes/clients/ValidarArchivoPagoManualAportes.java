package com.asopagos.aportes.clients;

import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.aportes.ResultadoArchivoAporteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/validarArchivoPagoManualAportes
 */
public class ValidarArchivoPagoManualAportes extends ServiceClient { 
    	private InformacionArchivoDTO archivoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoArchivoAporteDTO result;
  
 	public ValidarArchivoPagoManualAportes (InformacionArchivoDTO archivoDTO){
 		super();
		this.archivoDTO=archivoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(archivoDTO == null ? null : Entity.json(archivoDTO));
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

 
  
  	public void setArchivoDTO (InformacionArchivoDTO archivoDTO){
 		this.archivoDTO=archivoDTO;
 	}
 	
 	public InformacionArchivoDTO getArchivoDTO (){
 		return archivoDTO;
 	}
  
}