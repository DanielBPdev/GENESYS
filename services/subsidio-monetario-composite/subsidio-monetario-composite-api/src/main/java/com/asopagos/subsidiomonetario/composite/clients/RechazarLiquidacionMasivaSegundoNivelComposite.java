package com.asopagos.subsidiomonetario.composite.clients;

import com.asopagos.subsidiomonetario.dto.AprobacionRechazoSubsidioMonetarioDTO;
import java.util.Map;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetarioComposite/rechazarLiquidacionMasivaSegundoNivelComposite/{numeroSolicitud}/{idTarea}
 */
public class RechazarLiquidacionMasivaSegundoNivelComposite extends ServiceClient { 
  	private String numeroSolicitud;
  	private String idTarea;
    	private AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public RechazarLiquidacionMasivaSegundoNivelComposite (String numeroSolicitud,String idTarea,AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO){
 		super();
		this.numeroSolicitud=numeroSolicitud;
		this.idTarea=idTarea;
		this.aprobacionRechazoSubsidioMonetarioDTO=aprobacionRechazoSubsidioMonetarioDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroSolicitud", numeroSolicitud)
			.resolveTemplate("idTarea", idTarea)
			.request(MediaType.APPLICATION_JSON)
			.post(aprobacionRechazoSubsidioMonetarioDTO == null ? null : Entity.json(aprobacionRechazoSubsidioMonetarioDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,String> getResult() {
		return result;
	}

 	public void setNumeroSolicitud (String numeroSolicitud){
 		this.numeroSolicitud=numeroSolicitud;
 	}
 	
 	public String getNumeroSolicitud (){
 		return numeroSolicitud;
 	}
  	public void setIdTarea (String idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public String getIdTarea (){
 		return idTarea;
 	}
  
  
  	public void setAprobacionRechazoSubsidioMonetarioDTO (AprobacionRechazoSubsidioMonetarioDTO aprobacionRechazoSubsidioMonetarioDTO){
 		this.aprobacionRechazoSubsidioMonetarioDTO=aprobacionRechazoSubsidioMonetarioDTO;
 	}
 	
 	public AprobacionRechazoSubsidioMonetarioDTO getAprobacionRechazoSubsidioMonetarioDTO (){
 		return aprobacionRechazoSubsidioMonetarioDTO;
 	}
  
}