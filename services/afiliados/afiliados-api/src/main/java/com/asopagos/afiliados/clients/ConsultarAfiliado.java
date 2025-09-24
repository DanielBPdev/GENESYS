package com.asopagos.afiliados.clients;

import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.dto.ConsultarAfiliadoOutDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados
 */
public class ConsultarAfiliado extends ServiceClient {
 
  
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Boolean ubicacionesIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ConsultarAfiliadoOutDTO result;
  
 	public ConsultarAfiliado (String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,Boolean ubicacionesIdentificacion){
 		super();
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.ubicacionesIdentificacion=ubicacionesIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("ubicacionesIdentificacion", ubicacionesIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ConsultarAfiliadoOutDTO) response.readEntity(ConsultarAfiliadoOutDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ConsultarAfiliadoOutDTO getResult() {
		return result;
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
  	public void setUbicacionesIdentificacion (Boolean ubicacionesIdentificacion){
 		this.ubicacionesIdentificacion=ubicacionesIdentificacion;
 	}
 	
 	public Boolean getUbicacionesIdentificacion (){
 		return ubicacionesIdentificacion;
 	}
  
}