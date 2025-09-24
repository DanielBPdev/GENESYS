package com.asopagos.afiliaciones.empleadores.web.composite.clients;

import com.asopagos.enumeraciones.ResultadoRegistroContactoEnum;
import com.asopagos.dto.DigitarInformacionContactoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;
import com.asopagos.dto.webservices.AfiliacionEmpleadorDTO;
import java.util.Map;

public class DigitarYRadicarSolicitudAfiliacionWS extends ServiceClient {
    
    private AfiliacionEmpleadorDTO entrada;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,Object> result;
  
 	public DigitarYRadicarSolicitudAfiliacionWS (AfiliacionEmpleadorDTO entrada){
 		super();
		this.entrada=entrada;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(entrada == null ? null : Entity.json(entrada));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Map<String,Object>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Map<String,Object> getResult() {
		return result;
	}

 
  
  	public void setEntrada (AfiliacionEmpleadorDTO entrada){
 		this.entrada=entrada;
 	}
 	
 	public AfiliacionEmpleadorDTO getEntrada (){
 		return entrada;
 	}
 
}
