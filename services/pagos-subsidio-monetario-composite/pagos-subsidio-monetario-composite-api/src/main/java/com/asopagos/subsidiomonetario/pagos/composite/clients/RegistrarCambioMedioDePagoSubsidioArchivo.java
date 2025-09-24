/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asopagos.subsidiomonetario.pagos.composite.clients;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.subsidiomonetario.pagos.dto.RegistroCambioMedioDePagoDTO;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author linam
 */
public class RegistrarCambioMedioDePagoSubsidioArchivo extends ServiceClient { 
  	private Long idAdminSubsidio;
    	private RegistroCambioMedioDePagoDTO registroCambioMedioDePagoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  /*
 	public RegistrarCambioMedioDePagoSubsidioArchivo (Long idAdminSubsidio,RegistroCambioMedioDePagoDTO registroCambioMedioDePagoDTO){
 		super();
		this.idAdminSubsidio=idAdminSubsidio;
		this.registroCambioMedioDePagoDTO=registroCambioMedioDePagoDTO;
 	}
        
     */
        
       public RegistrarCambioMedioDePagoSubsidioArchivo (Long idAdminSubsidio,RegistroCambioMedioDePagoDTO registroCambioMedioDePagoDTO){
 		super();
                this.idAdminSubsidio=idAdminSubsidio;
                this.registroCambioMedioDePagoDTO=registroCambioMedioDePagoDTO;
 	}
 
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idAdminSubsidio", idAdminSubsidio)
			.request(MediaType.APPLICATION_JSON)
			.post(registroCambioMedioDePagoDTO == null ? null : Entity.json(registroCambioMedioDePagoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Long> getResult() {
		return result;
	}

 	public void setIdAdminSubsidio (Long idAdminSubsidio){
 		this.idAdminSubsidio=idAdminSubsidio;
 	}
 	
 	public Long getIdAdminSubsidio (){
 		return idAdminSubsidio;
 	}
  
  
  	public void setRegistroCambioMedioDePagoDTO (RegistroCambioMedioDePagoDTO registroCambioMedioDePagoDTO){
 		this.registroCambioMedioDePagoDTO=registroCambioMedioDePagoDTO;
 	}
 	
 	public RegistroCambioMedioDePagoDTO getRegistroCambioMedioDePagoDTO (){
 		return registroCambioMedioDePagoDTO;
 	}
  
}
