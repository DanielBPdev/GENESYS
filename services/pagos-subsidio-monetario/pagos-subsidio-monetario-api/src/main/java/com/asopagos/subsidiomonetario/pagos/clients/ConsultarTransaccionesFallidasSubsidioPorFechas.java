package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.TransaccionFallidaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarTransaccionesFallidasSubsidioPorFechas
 */
public class ConsultarTransaccionesFallidasSubsidioPorFechas extends ServiceClient {
 
  
  	private Long fechaInicial;
  	private Long fechaFinal;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TransaccionFallidaDTO> result;
  
 	public ConsultarTransaccionesFallidasSubsidioPorFechas (Long fechaInicial,Long fechaFinal){
 		super();
		this.fechaInicial=fechaInicial;
		this.fechaFinal=fechaFinal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("fechaInicial", fechaInicial)
						.queryParam("fechaFinal", fechaFinal)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<TransaccionFallidaDTO>) response.readEntity(new GenericType<List<TransaccionFallidaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<TransaccionFallidaDTO> getResult() {
		return result;
	}

 
  	public void setFechaInicial (Long fechaInicial){
 		this.fechaInicial=fechaInicial;
 	}
 	
 	public Long getFechaInicial (){
 		return fechaInicial;
 	}
  	public void setFechaFinal (Long fechaFinal){
 		this.fechaFinal=fechaFinal;
 	}
 	
 	public Long getFechaFinal (){
 		return fechaFinal;
 	}
  
}