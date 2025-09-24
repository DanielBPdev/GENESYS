package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO;
import java.lang.Boolean;
import java.lang.String;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/registroInformacion/rechazoSegundoNivel/{numeroSolicitud}
 */
public class RechazarLiquidacionMasivaSegundoNivel extends ServiceClient { 
  	private String numeroSolicitud;
   	private Boolean isAsync;
   	private AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudLiquidacionSubsidioModeloDTO result;
  
 	public RechazarLiquidacionMasivaSegundoNivel (String numeroSolicitud,Boolean isAsync,AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO){
 		super();
		this.numeroSolicitud=numeroSolicitud;
		this.isAsync=isAsync;
		this.aprobacionRechazoSubsidioMonetarioDTO=aprobacionRechazoSubsidioMonetarioDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroSolicitud", numeroSolicitud)
			.queryParam("isAsync", isAsync)
			.request(MediaType.APPLICATION_JSON)
			.post(aprobacionRechazoSubsidioMonetarioDTO == null ? null : Entity.json(aprobacionRechazoSubsidioMonetarioDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudLiquidacionSubsidioModeloDTO) response.readEntity(SolicitudLiquidacionSubsidioModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudLiquidacionSubsidioModeloDTO getResult() {
		return result;
	}

 	public void setNumeroSolicitud (String numeroSolicitud){
 		this.numeroSolicitud=numeroSolicitud;
 	}
 	
 	public String getNumeroSolicitud (){
 		return numeroSolicitud;
 	}
  
  	public void setIsAsync (Boolean isAsync){
 		this.isAsync=isAsync;
 	}
 	
 	public Boolean getIsAsync (){
 		return isAsync;
 	}
  
  	public void setAprobacionRechazoSubsidioMonetarioDTO (AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO){
 		this.aprobacionRechazoSubsidioMonetarioDTO=aprobacionRechazoSubsidioMonetarioDTO;
 	}
 	
 	public AprobacionRechazoSubsidioMonetarioDTO getAprobacionRechazoSubsidioMonetarioDTO (){
 		return aprobacionRechazoSubsidioMonetarioDTO;
 	}
  
}