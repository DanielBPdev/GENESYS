package com.asopagos.subsidiomonetario.pagos.clients;

import java.math.BigDecimal;
import com.asopagos.subsidiomonetario.pagos.enums.ComparacionSaldoTarjetaEnum;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/realizarComparacionSaldoTarjetas
 */
public class RealizarComparacionSaldoTarjetas extends ServiceClient {
 
  
  	private Long idPersona;
  	private BigDecimal saldoNuevaTarjeta;
  	private String numeroTarjeta;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ComparacionSaldoTarjetaEnum result;
  
 	public RealizarComparacionSaldoTarjetas (Long idPersona,BigDecimal saldoNuevaTarjeta,String numeroTarjeta){
 		super();
		this.idPersona=idPersona;
		this.saldoNuevaTarjeta=saldoNuevaTarjeta;
		this.numeroTarjeta=numeroTarjeta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPersona", idPersona)
						.queryParam("saldoNuevaTarjeta", saldoNuevaTarjeta)
						.queryParam("numeroTarjeta", numeroTarjeta)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ComparacionSaldoTarjetaEnum) response.readEntity(ComparacionSaldoTarjetaEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ComparacionSaldoTarjetaEnum getResult() {
		return result;
	}

 
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  	public void setSaldoNuevaTarjeta (BigDecimal saldoNuevaTarjeta){
 		this.saldoNuevaTarjeta=saldoNuevaTarjeta;
 	}
 	
 	public BigDecimal getSaldoNuevaTarjeta (){
 		return saldoNuevaTarjeta;
 	}
  	public void setNumeroTarjeta (String numeroTarjeta){
 		this.numeroTarjeta=numeroTarjeta;
 	}
 	
 	public String getNumeroTarjeta (){
 		return numeroTarjeta;
 	}
  
}