package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.fovis.dto.CartasAsignacionDTO;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarCartasAsignacionPorCicloAnoAsignacion
 */
public class ConsultarCartasAsignacionPorCicloAnoAsignacion extends ServiceClient {
 
  
  	private Integer anoAsignacion;
  	private Long cicloAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CartasAsignacionDTO> result;
  
 	public ConsultarCartasAsignacionPorCicloAnoAsignacion (Integer anoAsignacion,Long cicloAsignacion){
 		super();
		this.anoAsignacion=anoAsignacion;
		this.cicloAsignacion=cicloAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("anoAsignacion", anoAsignacion)
						.queryParam("cicloAsignacion", cicloAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CartasAsignacionDTO>) response.readEntity(new GenericType<List<CartasAsignacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CartasAsignacionDTO> getResult() {
		return result;
	}

 
  	public void setAnoAsignacion (Integer anoAsignacion){
 		this.anoAsignacion=anoAsignacion;
 	}
 	
 	public Integer getAnoAsignacion (){
 		return anoAsignacion;
 	}
  	public void setCicloAsignacion (Long cicloAsignacion){
 		this.cicloAsignacion=cicloAsignacion;
 	}
 	
 	public Long getCicloAsignacion (){
 		return cicloAsignacion;
 	}
  
}