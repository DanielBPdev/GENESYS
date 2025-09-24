package com.asopagos.empleadores.clients;

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
 * /rest/empleadores/datosTemporales
 */
public class GuardarDatosTemporalesEmpleador extends ServiceClient { 
   	private Long idSolicitudGlobal;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
   	private String jsonPayload;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public GuardarDatosTemporalesEmpleador (Long idSolicitudGlobal,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion,String jsonPayload){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.jsonPayload=jsonPayload;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitudGlobal", idSolicitudGlobal)
			.queryParam("numeroIdentificacion", numeroIdentificacion)
			.queryParam("tipoIdentificacion", tipoIdentificacion)
			.request(MediaType.APPLICATION_JSON)
			.post(jsonPayload == null ? null : Entity.json(jsonPayload));
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

 
  	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
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
  
  	public void setJsonPayload (String jsonPayload){
 		this.jsonPayload=jsonPayload;
 	}
 	
 	public String getJsonPayload (){
 		return jsonPayload;
 	}
  
}