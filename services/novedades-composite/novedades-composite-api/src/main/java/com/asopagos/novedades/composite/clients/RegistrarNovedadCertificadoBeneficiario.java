package com.asopagos.novedades.composite.clients;

import com.asopagos.novedades.composite.dto.ProcesarNovedadCargueArchivoDTO;
import com.asopagos.novedades.dto.SolicitudNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/registrarNovedadCertificadoBeneficiario
 */
public class RegistrarNovedadCertificadoBeneficiario extends ServiceClient { 
    	private ProcesarNovedadCargueArchivoDTO procesarNovedadDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudNovedadDTO result;
  
 	public RegistrarNovedadCertificadoBeneficiario (ProcesarNovedadCargueArchivoDTO procesarNovedadDTO){
 		super();
		this.procesarNovedadDTO=procesarNovedadDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(procesarNovedadDTO == null ? null : Entity.json(procesarNovedadDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudNovedadDTO) response.readEntity(SolicitudNovedadDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudNovedadDTO getResult() {
		return result;
	}

 
  
  	public void setProcesarNovedadDTO (ProcesarNovedadCargueArchivoDTO procesarNovedadDTO){
 		this.procesarNovedadDTO=procesarNovedadDTO;
 	}
 	
 	public ProcesarNovedadCargueArchivoDTO getProcesarNovedadDTO (){
 		return procesarNovedadDTO;
 	}
  
}