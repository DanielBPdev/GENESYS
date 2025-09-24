package com.asopagos.fovis.composite.clients;

import java.math.BigDecimal;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/asignacionFovisComposite/calcularGuardarCalificacionHogaresCiclo
 */
public class CalcularGuardarCalificacionHogaresCiclo extends ServiceClient {
 
  
  	private BigDecimal valorDisponible;
  	private Long idCicloAsignacion;
  
  
 	public CalcularGuardarCalificacionHogaresCiclo (BigDecimal valorDisponible,Long idCicloAsignacion){
 		super();
		this.valorDisponible=valorDisponible;
		this.idCicloAsignacion=idCicloAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("valorDisponible", valorDisponible)
						.queryParam("idCicloAsignacion", idCicloAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setValorDisponible (BigDecimal valorDisponible){
 		this.valorDisponible=valorDisponible;
 	}
 	
 	public BigDecimal getValorDisponible (){
 		return valorDisponible;
 	}
  	public void setIdCicloAsignacion (Long idCicloAsignacion){
 		this.idCicloAsignacion=idCicloAsignacion;
 	}
 	
 	public Long getIdCicloAsignacion (){
 		return idCicloAsignacion;
 	}
  
}