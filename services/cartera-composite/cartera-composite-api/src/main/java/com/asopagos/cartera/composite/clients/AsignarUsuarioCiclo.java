package com.asopagos.cartera.composite.clients;

import com.asopagos.dto.cartera.SimulacionDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/asignarUsuarioCiclo
 */
public class AsignarUsuarioCiclo extends ServiceClient { 
   	private ProcesoEnum proceso;
   	private List<SimulacionDTO> simulacionDTOs;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SimulacionDTO> result;
  
 	public AsignarUsuarioCiclo (ProcesoEnum proceso,List<SimulacionDTO> simulacionDTOs){
 		super();
		this.proceso=proceso;
		this.simulacionDTOs=simulacionDTOs;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("proceso", proceso)
			.request(MediaType.APPLICATION_JSON)
			.post(simulacionDTOs == null ? null : Entity.json(simulacionDTOs));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<SimulacionDTO>) response.readEntity(new GenericType<List<SimulacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<SimulacionDTO> getResult() {
		return result;
	}

 
  	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  
  	public void setSimulacionDTOs (List<SimulacionDTO> simulacionDTOs){
 		this.simulacionDTOs=simulacionDTOs;
 	}
 	
 	public List<SimulacionDTO> getSimulacionDTOs (){
 		return simulacionDTOs;
 	}
  
}