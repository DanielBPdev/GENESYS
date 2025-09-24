package com.asopagos.pila.clients;

import com.asopagos.pila.dto.OperadorInformacionDTO;
import com.asopagos.pila.dto.RespuestaServicioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/iniciarDescargaAutomatica
 */
public class IniciarDescargaAutomatica extends ServiceClient { 
    	private OperadorInformacionDTO operadorInformacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaServicioDTO result;
  
 	public IniciarDescargaAutomatica (OperadorInformacionDTO operadorInformacionDTO){
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
		result = (RespuestaServicioDTO) response.readEntity(RespuestaServicioDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public RespuestaServicioDTO getResult() {
		return result;
	}

 
  
  	public void setOperadorInformacionDTO (OperadorInformacionDTO operadorInformacionDTO){
 		this.operadorInformacionDTO=operadorInformacionDTO;
 	}
 	
 	public OperadorInformacionDTO getOperadorInformacionDTO (){
 		return operadorInformacionDTO;
 	}
  
}