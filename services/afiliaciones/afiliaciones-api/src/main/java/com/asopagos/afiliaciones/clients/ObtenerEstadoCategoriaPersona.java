package com.asopagos.afiliaciones.clients;

import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.afiliaciones.dto.CategoriaPersonaOutDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/afiliacion/obtenerEstadoCategoriaPersona
 */
public class ObtenerEstadoCategoriaPersona extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoID;
  	private String fechaInicio;
  	private String identificacion;
  	private String fechaFinal;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CategoriaPersonaOutDTO result;
  
 	public ObtenerEstadoCategoriaPersona (TipoIdentificacionEnum tipoID,String fechaInicio,String identificacion,String fechaFinal){
 		super();
		this.tipoID=tipoID;
		this.fechaInicio=fechaInicio;
		this.identificacion=identificacion;
		this.fechaFinal=fechaFinal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoID", tipoID)
						.queryParam("fechaInicio", fechaInicio)
						.queryParam("identificacion", identificacion)
						.queryParam("fechaFinal", fechaFinal)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CategoriaPersonaOutDTO) response.readEntity(CategoriaPersonaOutDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CategoriaPersonaOutDTO getResult() {
		return result;
	}

 
  	public void setTipoID (TipoIdentificacionEnum tipoID){
 		this.tipoID=tipoID;
 	}
 	
 	public TipoIdentificacionEnum getTipoID (){
 		return tipoID;
 	}
  	public void setFechaInicio (String fechaInicio){
 		this.fechaInicio=fechaInicio;
 	}
 	
 	public String getFechaInicio (){
 		return fechaInicio;
 	}
  	public void setIdentificacion (String identificacion){
 		this.identificacion=identificacion;
 	}
 	
 	public String getIdentificacion (){
 		return identificacion;
 	}
  	public void setFechaFinal (String fechaFinal){
 		this.fechaFinal=fechaFinal;
 	}
 	
 	public String getFechaFinal (){
 		return fechaFinal;
 	}
  
}