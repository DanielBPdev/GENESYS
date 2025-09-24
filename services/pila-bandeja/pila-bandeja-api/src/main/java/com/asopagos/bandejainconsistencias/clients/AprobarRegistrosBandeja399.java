package com.asopagos.bandejainconsistencias.clients;

import com.asopagos.bandejainconsistencias.dto.PreparacionAprobacion399DTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/aprobarRegistrosBandeja399
 */
public class AprobarRegistrosBandeja399 extends ServiceClient { 
    	private PreparacionAprobacion399DTO datosAprobacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private PreparacionAprobacion399DTO result;
  
 	public AprobarRegistrosBandeja399 (PreparacionAprobacion399DTO datosAprobacion){
 		super();
		this.datosAprobacion=datosAprobacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosAprobacion == null ? null : Entity.json(datosAprobacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (PreparacionAprobacion399DTO) response.readEntity(PreparacionAprobacion399DTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public PreparacionAprobacion399DTO getResult() {
		return result;
	}

 
  
  	public void setDatosAprobacion (PreparacionAprobacion399DTO datosAprobacion){
 		this.datosAprobacion=datosAprobacion;
 	}
 	
 	public PreparacionAprobacion399DTO getDatosAprobacion (){
 		return datosAprobacion;
 	}
  
}