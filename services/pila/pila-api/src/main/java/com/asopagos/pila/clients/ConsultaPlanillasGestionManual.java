package com.asopagos.pila.clients;

import com.asopagos.pila.dto.PlanillaGestionManualDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pila/consultarPlanillasManualPendientes
 */
public class ConsultaPlanillasGestionManual extends ServiceClient {
 
  
  	private Long numeroPlanilla;
  	private Long fechaIngreso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PlanillaGestionManualDTO> result;
  
 	public ConsultaPlanillasGestionManual (Long numeroPlanilla,Long fechaIngreso){
 		super();
		this.numeroPlanilla=numeroPlanilla;
		this.fechaIngreso=fechaIngreso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroPlanilla", numeroPlanilla)
						.queryParam("fechaIngreso", fechaIngreso)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PlanillaGestionManualDTO>) response.readEntity(new GenericType<List<PlanillaGestionManualDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PlanillaGestionManualDTO> getResult() {
		return result;
	}

 
  	public void setNumeroPlanilla (Long numeroPlanilla){
 		this.numeroPlanilla=numeroPlanilla;
 	}
 	
 	public Long getNumeroPlanilla (){
 		return numeroPlanilla;
 	}
  	public void setFechaIngreso (Long fechaIngreso){
 		this.fechaIngreso=fechaIngreso;
 	}
 	
 	public Long getFechaIngreso (){
 		return fechaIngreso;
 	}
  
}