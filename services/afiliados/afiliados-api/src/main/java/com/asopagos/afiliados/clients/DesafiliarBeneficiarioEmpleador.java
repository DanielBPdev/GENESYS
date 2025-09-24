package com.asopagos.afiliados.clients;

import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionBeneficiarioEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/desafiliarBeneficiarioEmpleador/{idEmpleador}
 */
public class DesafiliarBeneficiarioEmpleador extends ServiceClient { 
  	private Long idEmpleador;
   	private MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion;
   
  
 	public DesafiliarBeneficiarioEmpleador (Long idEmpleador,MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion){
 		super();
		this.idEmpleador=idEmpleador;
		this.motivoDesafiliacion=motivoDesafiliacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idEmpleador", idEmpleador)
			.queryParam("motivoDesafiliacion", motivoDesafiliacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  	public void setMotivoDesafiliacion (MotivoDesafiliacionBeneficiarioEnum motivoDesafiliacion){
 		this.motivoDesafiliacion=motivoDesafiliacion;
 	}
 	
 	public MotivoDesafiliacionBeneficiarioEnum getMotivoDesafiliacion (){
 		return motivoDesafiliacion;
 	}
  
  
}