package com.asopagos.reportes.clients;

import com.asopagos.enumeraciones.personas.TipoAfiliadoEnum;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/reportes/consultarEstadoAfiliadoFecha
 */
public class ConsultarEstadoAfiliadoFecha extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdentificacionEmpleador;
  	private String numeroIdentificacionEmpleador;
  	private TipoAfiliadoEnum tipoAfiliado;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private Long fecha;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ConsultarEstadoAfiliadoFecha (TipoIdentificacionEnum tipoIdentificacionEmpleador,String numeroIdentificacionEmpleador,TipoAfiliadoEnum tipoAfiliado,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,Long fecha){
 		super();
		this.tipoIdentificacionEmpleador=tipoIdentificacionEmpleador;
		this.numeroIdentificacionEmpleador=numeroIdentificacionEmpleador;
		this.tipoAfiliado=tipoAfiliado;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.fecha=fecha;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacionEmpleador", tipoIdentificacionEmpleador)
						.queryParam("numeroIdentificacionEmpleador", numeroIdentificacionEmpleador)
						.queryParam("tipoAfiliado", tipoAfiliado)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("fecha", fecha)
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

 
  	public void setTipoIdentificacionEmpleador (TipoIdentificacionEnum tipoIdentificacionEmpleador){
 		this.tipoIdentificacionEmpleador=tipoIdentificacionEmpleador;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacionEmpleador (){
 		return tipoIdentificacionEmpleador;
 	}
  	public void setNumeroIdentificacionEmpleador (String numeroIdentificacionEmpleador){
 		this.numeroIdentificacionEmpleador=numeroIdentificacionEmpleador;
 	}
 	
 	public String getNumeroIdentificacionEmpleador (){
 		return numeroIdentificacionEmpleador;
 	}
  	public void setTipoAfiliado (TipoAfiliadoEnum tipoAfiliado){
 		this.tipoAfiliado=tipoAfiliado;
 	}
 	
 	public TipoAfiliadoEnum getTipoAfiliado (){
 		return tipoAfiliado;
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
  	public void setFecha (Long fecha){
 		this.fecha=fecha;
 	}
 	
 	public Long getFecha (){
 		return fecha;
 	}
  
}