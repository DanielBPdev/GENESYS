package com.asopagos.pila.clients;

import java.math.BigDecimal;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/archivosPILA/calcularInteresesDeMora
 */
public class CalcularInteresesDeMora extends ServiceClient {
 
  
  	private BigDecimal valorAporte;
  	private String periodo;
  	private Long fechaVencimiento;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private BigDecimal result;
  
 	public CalcularInteresesDeMora (BigDecimal valorAporte,String periodo,Long fechaVencimiento){
 		super();
		this.valorAporte=valorAporte;
		this.periodo=periodo;
		this.fechaVencimiento=fechaVencimiento;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("valorAporte", valorAporte)
						.queryParam("periodo", periodo)
						.queryParam("fechaVencimiento", fechaVencimiento)
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

 
  	public void setValorAporte (BigDecimal valorAporte){
 		this.valorAporte=valorAporte;
 	}
 	
 	public BigDecimal getValorAporte (){
 		return valorAporte;
 	}
  	public void setPeriodo (String periodo){
 		this.periodo=periodo;
 	}
 	
 	public String getPeriodo (){
 		return periodo;
 	}
  	public void setFechaVencimiento (Long fechaVencimiento){
 		this.fechaVencimiento=fechaVencimiento;
 	}
 	
 	public Long getFechaVencimiento (){
 		return fechaVencimiento;
 	}
  
}