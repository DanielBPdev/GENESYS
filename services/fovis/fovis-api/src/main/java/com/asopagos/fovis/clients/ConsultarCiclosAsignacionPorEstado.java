package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.fovis.EstadoCicloAsignacionEnum;
import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarCiclosAsignacionPorEstado
 */
public class ConsultarCiclosAsignacionPorEstado extends ServiceClient {
 
  
  	private EstadoCicloAsignacionEnum estadoCicloAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CicloAsignacionModeloDTO> result;
  
 	public ConsultarCiclosAsignacionPorEstado (EstadoCicloAsignacionEnum estadoCicloAsignacion){
 		super();
		this.estadoCicloAsignacion=estadoCicloAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("estadoCicloAsignacion", estadoCicloAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CicloAsignacionModeloDTO>) response.readEntity(new GenericType<List<CicloAsignacionModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CicloAsignacionModeloDTO> getResult() {
		return result;
	}

 
  	public void setEstadoCicloAsignacion (EstadoCicloAsignacionEnum estadoCicloAsignacion){
 		this.estadoCicloAsignacion=estadoCicloAsignacion;
 	}
 	
 	public EstadoCicloAsignacionEnum getEstadoCicloAsignacion (){
 		return estadoCicloAsignacion;
 	}
  
}