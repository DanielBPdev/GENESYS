package com.asopagos.cartera.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarEstadoSolicitudGestionCobro
 */
public class ConsultarEstadoSolicitudGestionCobro extends ServiceClient {
 
  
  	private TipoAccionCobroEnum tipoAccionCobro;
  	private Long idCartera;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ConsultarEstadoSolicitudGestionCobro (TipoAccionCobroEnum tipoAccionCobro,Long idCartera){
 		super();
		this.tipoAccionCobro=tipoAccionCobro;
		this.idCartera=idCartera;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoAccionCobro", tipoAccionCobro)
						.queryParam("idCartera", idCartera)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public String getResult() {
		return result;
	}

 
  	public void setTipoAccionCobro (TipoAccionCobroEnum tipoAccionCobro){
 		this.tipoAccionCobro=tipoAccionCobro;
 	}
 	
 	public TipoAccionCobroEnum getTipoAccionCobro (){
 		return tipoAccionCobro;
 	}
  	public void setIdCartera (Long idCartera){
 		this.idCartera=idCartera;
 	}
 	
 	public Long getIdCartera (){
 		return idCartera;
 	}
  
}