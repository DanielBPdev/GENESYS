package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.SimulacionDTO;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.CicloCarteraModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/crearNuevoCicloFiscalizacion
 */
public class CrearNuevoCicloFiscalizacion extends ServiceClient { 
   	private Long fechaFin;
  	private Long fechaInicio;
   	private List<SimulacionDTO> simulacionDTOs;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CicloCarteraModeloDTO result;
  
 	public CrearNuevoCicloFiscalizacion (Long fechaFin,Long fechaInicio,List<SimulacionDTO> simulacionDTOs){
 		super();
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
		this.simulacionDTOs=simulacionDTOs;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("fechaFin", fechaFin)
			.queryParam("fechaInicio", fechaInicio)
			.request(MediaType.APPLICATION_JSON)
			.post(simulacionDTOs == null ? null : Entity.json(simulacionDTOs));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (CicloCarteraModeloDTO) response.readEntity(CicloCarteraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public CicloCarteraModeloDTO getResult() {
		return result;
	}

 
  	public void setFechaFin (Long fechaFin){
 		this.fechaFin=fechaFin;
 	}
 	
 	public Long getFechaFin (){
 		return fechaFin;
 	}
  	public void setFechaInicio (Long fechaInicio){
 		this.fechaInicio=fechaInicio;
 	}
 	
 	public Long getFechaInicio (){
 		return fechaInicio;
 	}
  
  	public void setSimulacionDTOs (List<SimulacionDTO> simulacionDTOs){
 		this.simulacionDTOs=simulacionDTOs;
 	}
 	
 	public List<SimulacionDTO> getSimulacionDTOs (){
 		return simulacionDTOs;
 	}
  
}