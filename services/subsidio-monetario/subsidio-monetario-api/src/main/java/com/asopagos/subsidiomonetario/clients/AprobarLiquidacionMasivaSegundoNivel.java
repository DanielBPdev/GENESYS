package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO;
import java.lang.String;
import com.asopagos.subsidiomonetario.modelo.dto.SolicitudLiquidacionSubsidioModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/registroInformacion/aprobacionSegundoNivel/{numeroSolicitud}
 */
public class AprobarLiquidacionMasivaSegundoNivel extends ServiceClient { 
  	private String numeroSolicitud;
    	private AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudLiquidacionSubsidioModeloDTO result;
  
 	public AprobarLiquidacionMasivaSegundoNivel (String numeroSolicitud,AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO){
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
  
  
  	public void setAprobacionRechazoSubsidioMonetarioDTO (AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO){
 		this.aprobacionRechazoSubsidioMonetarioDTO=aprobacionRechazoSubsidioMonetarioDTO;
 	}
 	
 	public AprobacionRechazoSubsidioMonetarioDTO getAprobacionRechazoSubsidioMonetarioDTO (){
 		return aprobacionRechazoSubsidioMonetarioDTO;
 	}
  
}