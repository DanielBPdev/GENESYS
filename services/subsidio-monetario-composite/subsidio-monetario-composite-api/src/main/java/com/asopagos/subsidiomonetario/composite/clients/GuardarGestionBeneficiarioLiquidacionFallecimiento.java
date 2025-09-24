package com.asopagos.subsidiomonetario.composite.clients;

import java.lang.Long;
import java.lang.String;
import com.asopagos.enumeraciones.subsidiomonetario.liquidacion.ConjuntoValidacionSubsidioEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetarioComposite/guardar/gestionBeneficiario/liquidacionFallecimiento/{numeroRadicacion}
 */
public class GuardarGestionBeneficiarioLiquidacionFallecimiento extends ServiceClient { 
  	private String numeroRadicacion;
   	private Long idCondicionPersona;
  	private ConjuntoValidacionSubsidioEnum validacion;
   
  
 	public GuardarGestionBeneficiarioLiquidacionFallecimiento (String numeroRadicacion,Long idCondicionPersona,ConjuntoValidacionSubsidioEnum validacion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.idCondicionPersona=idCondicionPersona;
		this.validacion=validacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("numeroRadicacion", numeroRadicacion)
			.queryParam("idCondicionPersona", idCondicionPersona)
			.queryParam("validacion", validacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  	public void setIdCondicionPersona (Long idCondicionPersona){
 		this.idCondicionPersona=idCondicionPersona;
 	}
 	
 	public Long getIdCondicionPersona (){
 		return idCondicionPersona;
 	}
  	public void setValidacion (ConjuntoValidacionSubsidioEnum validacion){
 		this.validacion=validacion;
 	}
 	
 	public ConjuntoValidacionSubsidioEnum getValidacion (){
 		return validacion;
 	}
  
  
}