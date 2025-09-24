package com.asopagos.subsidiomonetario.clients;

import java.math.BigDecimal;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarValorCuotaPeriodo
 */
public class ConsultarValorCuotaPeriodo extends ServiceClient {
 
  
  	private Long periodo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private BigDecimal result;
  
 	public ConsultarValorCuotaPeriodo (Long periodo){
 		super();
		this.periodo=periodo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("periodo", periodo)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (BigDecimal) response.readEntity(BigDecimal.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public BigDecimal getResult() {
		return result;
	}

 
  	public void setPeriodo (Long periodo){
 		this.periodo=periodo;
 	}
 	
 	public Long getPeriodo (){
 		return periodo;
 	}
  
}