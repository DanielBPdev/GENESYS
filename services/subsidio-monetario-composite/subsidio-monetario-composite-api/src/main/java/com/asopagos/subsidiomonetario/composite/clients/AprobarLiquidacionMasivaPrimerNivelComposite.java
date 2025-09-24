package com.asopagos.subsidiomonetario.composite.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetarioComposite/aprobarLiquidacionMasivaPrimerNivelComposite/{numeroSolicitud}/{idTarea}
 */
public class AprobarLiquidacionMasivaPrimerNivelComposite extends ServiceClient { 
  	private String numeroSolicitud;
  	private String idTarea;
   	private String usernameSupervisor;
   
  
 	public AprobarLiquidacionMasivaPrimerNivelComposite (String numeroSolicitud,String idTarea,String usernameSupervisor){
 		super();
		this.numeroSolicitud=numeroSolicitud;
		this.idTarea=idTarea;
		this.usernameSupervisor=usernameSupervisor;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroSolicitud", numeroSolicitud)
			.resolveTemplate("idTarea", idTarea)
			.queryParam("usernameSupervisor", usernameSupervisor)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
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
  
  	public void setUsernameSupervisor (String usernameSupervisor){
 		this.usernameSupervisor=usernameSupervisor;
 	}
 	
 	public String getUsernameSupervisor (){
 		return usernameSupervisor;
 	}
  
  
}