package com.asopagos.pila.composite.clients;

import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PilaComposite/registrarRelacionarAportesPlanilla
 */
public class RegistrarRelacionarAportesPlanilla extends ServiceClient {
 
  
  	private Long idPlanillaOriginal;
  	private Long idIndicePlanilla;
  	private Long idRegistroGeneral;
  
  
 	public RegistrarRelacionarAportesPlanilla (Long idPlanillaOriginal,Long idIndicePlanilla,Long idRegistroGeneral,Long idTransaccion){
 		super();
		this.idPlanillaOriginal=idPlanillaOriginal;
		this.idIndicePlanilla=idIndicePlanilla;
		this.idRegistroGeneral=idRegistroGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPlanillaOriginal", idPlanillaOriginal)
						.queryParam("idIndicePlanilla", idIndicePlanilla)
						.queryParam("idRegistroGeneral", idRegistroGeneral)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdPlanillaOriginal (Long idPlanillaOriginal){
 		this.idPlanillaOriginal=idPlanillaOriginal;
 	}
 	
 	public Long getIdPlanillaOriginal (){
 		return idPlanillaOriginal;
 	}
  	public void setIdIndicePlanilla (Long idIndicePlanilla){
 		this.idIndicePlanilla=idIndicePlanilla;
 	}
 	
 	public Long getIdIndicePlanilla (){
 		return idIndicePlanilla;
 	}
  	public void setIdRegistroGeneral (Long idRegistroGeneral){
 		this.idRegistroGeneral=idRegistroGeneral;
 	}
 	
 	public Long getIdRegistroGeneral (){
 		return idRegistroGeneral;
 	}
  
}