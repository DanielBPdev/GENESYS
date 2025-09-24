package com.asopagos.comunicados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Boolean;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.Object;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/comunicados/obtenerNotificacionPila
 */
public class ObtenerNotificacionPila extends ServiceClient { 
   	private Boolean esEmpleador;
  	private String etiquetaPlantillaComunicadoEnum;
  	private TipoIdentificacionEnum tipoIdentificacion;
  	private String numIdentificacion;
  	private String fechaEnvio;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Object> result;
  
 	public ObtenerNotificacionPila (Boolean esEmpleador,String etiquetaPlantillaComunicadoEnum,TipoIdentificacionEnum tipoIdentificacion,String numIdentificacion,String fechaEnvio){
 		super();
		this.esEmpleador=esEmpleador;
		this.etiquetaPlantillaComunicadoEnum=etiquetaPlantillaComunicadoEnum;
		this.tipoIdentificacion=tipoIdentificacion;
		this.numIdentificacion=numIdentificacion;
		this.fechaEnvio=fechaEnvio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("esEmpleador", esEmpleador)
			.queryParam("etiquetaPlantillaComunicadoEnum", etiquetaPlantillaComunicadoEnum)
			.queryParam("tipoIdentificacion", tipoIdentificacion)
			.queryParam("numIdentificacion", numIdentificacion)
			.queryParam("fechaEnvio", fechaEnvio)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Object>) response.readEntity(new GenericType<List<Object>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Object> getResult() {
		return result;
	}

 
  	public void setEsEmpleador (Boolean esEmpleador){
 		this.esEmpleador=esEmpleador;
 	}
 	
 	public Boolean getEsEmpleador (){
 		return esEmpleador;
 	}
  	public void setEtiquetaPlantillaComunicadoEnum (String etiquetaPlantillaComunicadoEnum){
 		this.etiquetaPlantillaComunicadoEnum=etiquetaPlantillaComunicadoEnum;
 	}
 	
 	public String getEtiquetaPlantillaComunicadoEnum (){
 		return etiquetaPlantillaComunicadoEnum;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setNumIdentificacion (String numIdentificacion){
 		this.numIdentificacion=numIdentificacion;
 	}
 	
 	public String getNumIdentificacion (){
 		return numIdentificacion;
 	}
  	public void setFechaEnvio (String fechaEnvio){
 		this.fechaEnvio=fechaEnvio;
 	}
 	
 	public String getFechaEnvio (){
 		return fechaEnvio;
 	}
  
  
}