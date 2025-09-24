package com.asopagos.cartera.clients;

import java.lang.Boolean;
import com.asopagos.enumeraciones.cartera.EstadoOperacionCarteraEnum;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarCondicionAportanteCarteraLCUNO
 */
public class ConsultarCondicionAportantesCarteraLCUNO extends ServiceClient {
 
  
  	private TipoLineaCobroEnum tipoLineaCobro;
  	private EstadoOperacionCarteraEnum estadoOperacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ConsultarCondicionAportantesCarteraLCUNO (TipoLineaCobroEnum tipoLineaCobro,EstadoOperacionCarteraEnum estadoOperacion){
 		super();
		this.tipoLineaCobro=tipoLineaCobro;
		this.estadoOperacion=estadoOperacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoLineaCobro", tipoLineaCobro)
						.queryParam("estadoOperacion", estadoOperacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Boolean getResult() {
		return result;
	}

 
  	public void setTipoLineaCobro (TipoLineaCobroEnum tipoLineaCobro){
 		this.tipoLineaCobro=tipoLineaCobro;
 	}
 	
 	public TipoLineaCobroEnum getTipoLineaCobro (){
 		return tipoLineaCobro;
 	}
  	public void setEstadoOperacion (EstadoOperacionCarteraEnum estadoOperacion){
 		this.estadoOperacion=estadoOperacion;
 	}
 	
 	public EstadoOperacionCarteraEnum getEstadoOperacion (){
 		return estadoOperacion;
 	}
  
}