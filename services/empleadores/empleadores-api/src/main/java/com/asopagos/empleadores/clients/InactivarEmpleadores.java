package com.asopagos.empleadores.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.afiliaciones.MotivoDesafiliacionEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/inactivarEmpleadores
 */
public class InactivarEmpleadores extends ServiceClient { 
   	private MotivoDesafiliacionEnum motivoDesafiliacion;
   	private List<Long> idEmpleadores;
  
  
 	public InactivarEmpleadores (MotivoDesafiliacionEnum motivoDesafiliacion,List<Long> idEmpleadores){
 		super();
		this.motivoDesafiliacion=motivoDesafiliacion;
		this.idEmpleadores=idEmpleadores;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("motivoDesafiliacion", motivoDesafiliacion)
			.request(MediaType.APPLICATION_JSON)
			.post(idEmpleadores == null ? null : Entity.json(idEmpleadores));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setMotivoDesafiliacion (MotivoDesafiliacionEnum motivoDesafiliacion){
 		this.motivoDesafiliacion=motivoDesafiliacion;
 	}
 	
 	public MotivoDesafiliacionEnum getMotivoDesafiliacion (){
 		return motivoDesafiliacion;
 	}
  
  	public void setIdEmpleadores (List<Long> idEmpleadores){
 		this.idEmpleadores=idEmpleadores;
 	}
 	
 	public List<Long> getIdEmpleadores (){
 		return idEmpleadores;
 	}
  
}