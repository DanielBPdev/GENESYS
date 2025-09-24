package com.asopagos.pila.clients;

import java.util.List;
import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/gestionarRegistrosPlanillasParaAgrupar/{agrupar}/{idIndicePlanilla}
 */
public class GestionarRegistrosPlanillasParaAgrupar extends ServiceClient { 
  	private Boolean agrupar;
  	private Long idIndicePlanilla;
    	private List<Long> idsRegistrosDetallados;
  
  
 	public GestionarRegistrosPlanillasParaAgrupar (Boolean agrupar,Long idIndicePlanilla,List<Long> idsRegistrosDetallados){
 		super();
		this.agrupar=agrupar;
		this.idIndicePlanilla=idIndicePlanilla;
		this.idsRegistrosDetallados=idsRegistrosDetallados;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("agrupar", agrupar)
			.resolveTemplate("idIndicePlanilla", idIndicePlanilla)
			.request(MediaType.APPLICATION_JSON)
			.post(idsRegistrosDetallados == null ? null : Entity.json(idsRegistrosDetallados));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setAgrupar (Boolean agrupar){
 		this.agrupar=agrupar;
 	}
 	
 	public Boolean getAgrupar (){
 		return agrupar;
 	}
  	public void setIdIndicePlanilla (Long idIndicePlanilla){
 		this.idIndicePlanilla=idIndicePlanilla;
 	}
 	
 	public Long getIdIndicePlanilla (){
 		return idIndicePlanilla;
 	}
  
  
  	public void setIdsRegistrosDetallados (List<Long> idsRegistrosDetallados){
 		this.idsRegistrosDetallados=idsRegistrosDetallados;
 	}
 	
 	public List<Long> getIdsRegistrosDetallados (){
 		return idsRegistrosDetallados;
 	}
  
}