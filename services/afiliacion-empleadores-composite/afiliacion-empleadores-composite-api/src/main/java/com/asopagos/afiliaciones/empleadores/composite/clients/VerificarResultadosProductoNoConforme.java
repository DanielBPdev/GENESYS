package com.asopagos.afiliaciones.empleadores.composite.clients;

import com.asopagos.afiliaciones.empleadores.composite.dto.VerificarResultadosProductoNoConformeDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/verificarResultadosProductoNoConforme/{idTarea}/terminar
 */
public class VerificarResultadosProductoNoConforme extends ServiceClient { 
  	private Long idTarea;
    	private VerificarResultadosProductoNoConformeDTO inDTO;
  
  
 	public VerificarResultadosProductoNoConforme (Long idTarea,VerificarResultadosProductoNoConformeDTO inDTO){
 		super();
		this.idTarea=idTarea;
		this.inDTO=inDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTarea", idTarea)
			.request(MediaType.APPLICATION_JSON)
			.post(inDTO == null ? null : Entity.json(inDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setIdTarea (Long idTarea){
 		this.idTarea=idTarea;
 	}
 	
 	public Long getIdTarea (){
 		return idTarea;
 	}
  
  
  	public void setInDTO (VerificarResultadosProductoNoConformeDTO inDTO){
 		this.inDTO=inDTO;
 	}
 	
 	public VerificarResultadosProductoNoConformeDTO getInDTO (){
 		return inDTO;
 	}
  
}