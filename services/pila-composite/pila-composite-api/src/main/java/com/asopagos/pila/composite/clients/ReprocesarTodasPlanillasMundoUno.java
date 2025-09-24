package com.asopagos.pila.composite.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.aportes.TipoOperadorEnum;
import java.lang.Short;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PilaComposite/reprocesarTodasPlanillasMundoUno
 */
public class ReprocesarTodasPlanillasMundoUno extends ServiceClient {
 
  
  	private Short digitoVerificacion;
  	private String numeroPlanilla;
  	private String bloqueValidacion;
  	private TipoOperadorEnum operador;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Long fechaFin;
  	private Long fechaInicio;
  	private Boolean ocultarBlq5;
  
  
 	public ReprocesarTodasPlanillasMundoUno (Short digitoVerificacion,String numeroPlanilla,String bloqueValidacion,TipoOperadorEnum operador,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,Long fechaFin,Long fechaInicio,Boolean ocultarBlq5){
 		super();
		this.digitoVerificacion=digitoVerificacion;
		this.numeroPlanilla=numeroPlanilla;
		this.bloqueValidacion=bloqueValidacion;
		this.operador=operador;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.fechaFin=fechaFin;
		this.fechaInicio=fechaInicio;
		this.ocultarBlq5=ocultarBlq5;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("digitoVerificacion", digitoVerificacion)
						.queryParam("numeroPlanilla", numeroPlanilla)
						.queryParam("bloqueValidacion", bloqueValidacion)
						.queryParam("operador", operador)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("fechaFin", fechaFin)
						.queryParam("fechaInicio", fechaInicio)
						.queryParam("ocultarBlq5", ocultarBlq5)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setDigitoVerificacion (Short digitoVerificacion){
 		this.digitoVerificacion=digitoVerificacion;
 	}
 	
 	public Short getDigitoVerificacion (){
 		return digitoVerificacion;
 	}
  	public void setNumeroPlanilla (String numeroPlanilla){
 		this.numeroPlanilla=numeroPlanilla;
 	}
 	
 	public String getNumeroPlanilla (){
 		return numeroPlanilla;
 	}
  	public void setBloqueValidacion (String bloqueValidacion){
 		this.bloqueValidacion=bloqueValidacion;
 	}
 	
 	public String getBloqueValidacion (){
 		return bloqueValidacion;
 	}
  	public void setOperador (TipoOperadorEnum operador){
 		this.operador=operador;
 	}
 	
 	public TipoOperadorEnum getOperador (){
 		return operador;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setFechaFin (Long fechaFin){
 		this.fechaFin=fechaFin;
 	}
 	
 	public Long getFechaFin (){
 		return fechaFin;
 	}
  	public void setFechaInicio (Long fechaInicio){
 		this.fechaInicio=fechaInicio;
 	}
 	
 	public Long getFechaInicio (){
 		return fechaInicio;
 	}
  	public void setOcultarBlq5 (Boolean ocultarBlq5){
 		this.ocultarBlq5=ocultarBlq5;
 	}
 	
 	public Boolean getOcultarBlq5 (){
 		return ocultarBlq5;
 	}
  
}