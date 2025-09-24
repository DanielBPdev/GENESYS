package com.asopagos.subsidiomonetario.pagos.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.RegistroCambioMedioDePagoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PagosSubsidioMonetarioComposite/registrar/cambioMedioDePago/{idAdminSubsidio}
 */
public class RegistrarCambioMedioDePagoSubsidio extends ServiceClient { 
  	private Long idAdminSubsidio;
    	private RegistroCambioMedioDePagoDTO registroCambioMedioDePagoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public RegistrarCambioMedioDePagoSubsidio (Long idAdminSubsidio,RegistroCambioMedioDePagoDTO registroCambioMedioDePagoDTO){
 		super();
		this.idAdminSubsidio=idAdminSubsidio;
		this.registroCambioMedioDePagoDTO=registroCambioMedioDePagoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idAdminSubsidio", idAdminSubsidio)
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