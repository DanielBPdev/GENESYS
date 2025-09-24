package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/registroInformacion/rechazoPrimerNivel/{numeroSolicitud}
 */
public class RechazarLiquidacionMasivaPrimerNivel extends ServiceClient { 
  	private String numeroSolicitud;
    	private AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public RechazarLiquidacionMasivaPrimerNivel (String numeroSolicitud,AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO){
 		super();
		this.numeroSolicitud=numeroSolicitud;
		this.aprobacionRechazoSubsidioMonetarioDTO=aprobacionRechazoSubsidioMonetarioDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroSolicitud", numeroSolicitud)
			.request(MediaType.APPLICATION_JSON)
			.post(aprobacionRechazoSubsidioMonetarioDTO == null ? null : Entity.json(aprobacionRechazoSubsidioMonetarioDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 	public void setNumeroSolicitud (String numeroSolicitud){
 		this.numeroSolicitud=numeroSolicitud;
 	}
 	
 	public String getNumeroSolicitud (){
 		return numeroSolicitud;
 	}
  
  
  	public void setAprobacionRechazoSubsidioMonetarioDTO (AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO){
 		this.aprobacionRechazoSubsidioMonetarioDTO=aprobacionRechazoSubsidioMonetarioDTO;
 	}
 	
 	public AprobacionRechazoSubsidioMonetarioDTO getAprobacionRechazoSubsidioMonetarioDTO (){
 		return aprobacionRechazoSubsidioMonetarioDTO;
 	}
  
}