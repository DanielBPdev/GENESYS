package com.asopagos.pila.clients;

import com.asopagos.pila.dto.OperadorInformacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/ejecutarProcesoDescargaCargaValidacionAutomatica
 */
public class EjecutarProcesoAutomatico extends ServiceClient { 
    	private OperadorInformacionDTO operadorInformacionDTO;
  
  
 	public EjecutarProcesoAutomatico (OperadorInformacionDTO operadorInformacionDTO){
 		super();
		this.operadorInformacionDTO=operadorInformacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(operadorInformacionDTO == null ? null : Entity.json(operadorInformacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setOperadorInformacionDTO (OperadorInformacionDTO operadorInformacionDTO){
 		this.operadorInformacionDTO=operadorInformacionDTO;
 	}
 	
 	public OperadorInformacionDTO getOperadorInformacionDTO (){
 		return operadorInformacionDTO;
 	}
  
}