package com.asopagos.subsidiomonetario.pagos.clients;

import java.util.List;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.EstadoTransaccionSubsidioEnum;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/dispersarPagos/estadoEnviado/{numeroRadicacion}
 */
public class DispersarPagosEstadoEnviado extends ServiceClient {
 
  	private String numeroRadicacion;
  
  	private EstadoTransaccionSubsidioEnum estadoTransaccion;
  	private List<TipoMedioDePagoEnum> mediosDePago;
  
  
 	public DispersarPagosEstadoEnviado (String numeroRadicacion,EstadoTransaccionSubsidioEnum estadoTransaccion,List<TipoMedioDePagoEnum> mediosDePago){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.estadoTransaccion=estadoTransaccion;
		this.mediosDePago=mediosDePago;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
									.queryParam("estadoTransaccion", estadoTransaccion)
						.queryParam("mediosDePago", mediosDePago.toArray())
						.request(MediaType.APPLICATION_JSON).get();
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
  
  	public void setEstadoTransaccion (EstadoTransaccionSubsidioEnum estadoTransaccion){
 		this.estadoTransaccion=estadoTransaccion;
 	}
 	
 	public EstadoTransaccionSubsidioEnum getEstadoTransaccion (){
 		return estadoTransaccion;
 	}
  	public void setMediosDePago (List<TipoMedioDePagoEnum> mediosDePago){
 		this.mediosDePago=mediosDePago;
 	}
 	
 	public List<TipoMedioDePagoEnum> getMediosDePago (){
 		return mediosDePago;
 	}
  
}