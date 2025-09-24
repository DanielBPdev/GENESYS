package com.asopagos.pila.clients;

import com.asopagos.enumeraciones.pila.TipoProcesoPilaEnum;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/archivosPILA/instanciarProceso
 */
public class InstanciarProceso extends ServiceClient { 
   	private String usuarioProceso;
  	private TipoProcesoPilaEnum tipoProceso;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public InstanciarProceso (String usuarioProceso,TipoProcesoPilaEnum tipoProceso){
 		super();
		this.usuarioProceso=usuarioProceso;
		this.tipoProceso=tipoProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("usuarioProceso", usuarioProceso)
			.queryParam("tipoProceso", tipoProceso)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  	public void setUsuarioProceso (String usuarioProceso){
 		this.usuarioProceso=usuarioProceso;
 	}
 	
 	public String getUsuarioProceso (){
 		return usuarioProceso;
 	}
  	public void setTipoProceso (TipoProcesoPilaEnum tipoProceso){
 		this.tipoProceso=tipoProceso;
 	}
 	
 	public TipoProcesoPilaEnum getTipoProceso (){
 		return tipoProceso;
 	}
  
  
}