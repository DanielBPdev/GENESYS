package com.asopagos.aportes.clients;

import com.asopagos.aportes.dto.EstadoServiciosIndPenDTO;
import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/invocarCalculoEstadoServiciosIndPen
 */
public class InvocarCalculoEstadoServiciosIndPen extends ServiceClient { 
   	private Long fechaReferencia;
  	private TipoAfiliadoEnum tipoAfiliacion;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EstadoServiciosIndPenDTO result;
  
 	public InvocarCalculoEstadoServiciosIndPen (Long fechaReferencia,TipoAfiliadoEnum tipoAfiliacion,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.fechaReferencia=fechaReferencia;
		this.tipoAfiliacion=tipoAfiliacion;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("fechaReferencia", fechaReferencia)
			.queryParam("tipoAfiliacion", tipoAfiliacion)
			.queryParam("numeroIdentificacion", numeroIdentificacion)
			.queryParam("tipoIdentificacion", tipoIdentificacion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (EstadoServiciosIndPenDTO) response.readEntity(EstadoServiciosIndPenDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public EstadoServiciosIndPenDTO getResult() {
		return result;
	}

 
  	public void setFechaReferencia (Long fechaReferencia){
 		this.fechaReferencia=fechaReferencia;
 	}
 	
 	public Long getFechaReferencia (){
 		return fechaReferencia;
 	}
  	public void setTipoAfiliacion (TipoAfiliadoEnum tipoAfiliacion){
 		this.tipoAfiliacion=tipoAfiliacion;
 	}
 	
 	public TipoAfiliadoEnum getTipoAfiliacion (){
 		return tipoAfiliacion;
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