package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Long;
import java.lang.String;
import com.asopagos.enumeraciones.subsidiomonetario.pagos.ResultadoGestionInconsistenciaEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/cerrarCasoInconsistenciaTarjeta
 */
public class CerrarCasoInconsistenciaTarjeta extends ServiceClient { 
   	private Long idRegistro;
  	private ResultadoGestionInconsistenciaEnum resultadoGestion;
   	private String detalleResolucion;
  
  
 	public CerrarCasoInconsistenciaTarjeta (Long idRegistro,ResultadoGestionInconsistenciaEnum resultadoGestion,String detalleResolucion){
 		super();
		this.idRegistro=idRegistro;
		this.resultadoGestion=resultadoGestion;
		this.detalleResolucion=detalleResolucion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idRegistro", idRegistro)
			.queryParam("resultadoGestion", resultadoGestion)
			.request(MediaType.APPLICATION_JSON)
			.post(detalleResolucion == null ? null : Entity.json(detalleResolucion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setIdRegistro (Long idRegistro){
 		this.idRegistro=idRegistro;
 	}
 	
 	public Long getIdRegistro (){
 		return idRegistro;
 	}
  	public void setResultadoGestion (ResultadoGestionInconsistenciaEnum resultadoGestion){
 		this.resultadoGestion=resultadoGestion;
 	}
 	
 	public ResultadoGestionInconsistenciaEnum getResultadoGestion (){
 		return resultadoGestion;
 	}
  
  	public void setDetalleResolucion (String detalleResolucion){
 		this.detalleResolucion=detalleResolucion;
 	}
 	
 	public String getDetalleResolucion (){
 		return detalleResolucion;
 	}
  
}