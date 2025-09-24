package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.aportes.EstadoRegistroAporteEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.aportes.EstadoAporteEnum;
import com.asopagos.dto.modelo.AporteGeneralModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarAporteGeneralEmpleador
 */
public class ConsultarAporteGeneralEmpleador extends ServiceClient {
 
  
  	private Long idEmpleador;
  	private EstadoAporteEnum estadoAporteAportante;
  	private EstadoRegistroAporteEnum estadoRegistroAporte;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AporteGeneralModeloDTO> result;
  
 	public ConsultarAporteGeneralEmpleador (Long idEmpleador,EstadoAporteEnum estadoAporteAportante,EstadoRegistroAporteEnum estadoRegistroAporte){
 		super();
		this.idEmpleador=idEmpleador;
		this.estadoAporteAportante=estadoAporteAportante;
		this.estadoRegistroAporte=estadoRegistroAporte;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idEmpleador", idEmpleador)
						.queryParam("estadoAporteAportante", estadoAporteAportante)
						.queryParam("estadoRegistroAporte", estadoRegistroAporte)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<AporteGeneralModeloDTO>) response.readEntity(new GenericType<List<AporteGeneralModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<AporteGeneralModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  	public void setEstadoAporteAportante (EstadoAporteEnum estadoAporteAportante){
 		this.estadoAporteAportante=estadoAporteAportante;
 	}
 	
 	public EstadoAporteEnum getEstadoAporteAportante (){
 		return estadoAporteAportante;
 	}
  	public void setEstadoRegistroAporte (EstadoRegistroAporteEnum estadoRegistroAporte){
 		this.estadoRegistroAporte=estadoRegistroAporte;
 	}
 	
 	public EstadoRegistroAporteEnum getEstadoRegistroAporte (){
 		return estadoRegistroAporte;
 	}
  
}