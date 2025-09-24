package com.asopagos.fovis.composite.clients;

import com.asopagos.enumeraciones.fovis.EstadoOferenteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovisComposite/cambiarEstadoOferente
 */
public class CambiarEstadoOferente extends ServiceClient {
 
  
  	private EstadoOferenteEnum nuevoEstado;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EstadoOferenteEnum result;
  
 	public CambiarEstadoOferente (EstadoOferenteEnum nuevoEstado,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.nuevoEstado=nuevoEstado;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("nuevoEstado", nuevoEstado)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EstadoOferenteEnum) response.readEntity(EstadoOferenteEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EstadoOferenteEnum getResult() {
		return result;
	}

 
  	public void setNuevoEstado (EstadoOferenteEnum nuevoEstado){
 		this.nuevoEstado=nuevoEstado;
 	}
 	
 	public EstadoOferenteEnum getNuevoEstado (){
 		return nuevoEstado;
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
  
}