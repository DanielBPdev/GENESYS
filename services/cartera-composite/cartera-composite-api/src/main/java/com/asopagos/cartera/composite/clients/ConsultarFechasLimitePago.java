package com.asopagos.cartera.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.Short;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/carteraComposite/consultarFechasLimitePago
 */
public class ConsultarFechasLimitePago extends ServiceClient {
 
  
  	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private String numeroIdentificacion;
  	private Short numeroCuotas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public ConsultarFechasLimitePago (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,String numeroIdentificacion,Short numeroCuotas){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.numeroIdentificacion=numeroIdentificacion;
		this.numeroCuotas=numeroCuotas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoSolicitante", tipoSolicitante)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("numeroCuotas", numeroCuotas)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Long> getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  	public void setNumeroIdentificacion (String numeroIdentificacion){
 		this.numeroIdentificacion=numeroIdentificacion;
 	}
 	
 	public String getNumeroIdentificacion (){
 		return numeroIdentificacion;
 	}
  	public void setNumeroCuotas (Short numeroCuotas){
 		this.numeroCuotas=numeroCuotas;
 	}
 	
 	public Short getNumeroCuotas (){
 		return numeroCuotas;
 	}
  
}