package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.EstadoFiscalizacionEnum;
import com.asopagos.dto.cartera.SimulacionDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarAportantesCiclo
 */
public class ConsultarAportantesCiclo extends ServiceClient {
 
  
  	private Long idPersona;
  	private List<EstadoFiscalizacionEnum> estado;
  	private List<String> analista;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SimulacionDTO> result;
  
 	public ConsultarAportantesCiclo (Long idPersona,List<EstadoFiscalizacionEnum> estado,List<String> analista){
 		super();
		this.idPersona=idPersona;
		this.estado=estado;
		this.analista=analista;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPersona", idPersona)
						.queryParam("estado", estado.toArray())
						.queryParam("analista", analista.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SimulacionDTO>) response.readEntity(new GenericType<List<SimulacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SimulacionDTO> getResult() {
		return result;
	}

 
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  	public void setEstado (List<EstadoFiscalizacionEnum> estado){
 		this.estado=estado;
 	}
 	
 	public List<EstadoFiscalizacionEnum> getEstado (){
 		return estado;
 	}
  	public void setAnalista (List<String> analista){
 		this.analista=analista;
 	}
 	
 	public List<String> getAnalista (){
 		return analista;
 	}
  
}