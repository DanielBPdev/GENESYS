package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.cartera.dto.IntegracionCarteraDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/cartera/obtenerEstadoCartera
 */
public class ObtenerEstadoCartera extends ServiceClient {
 
  
  	private String numeroIdentificacionAportante;
  	private String numeroIdentificacionDependiente;
  	private String periodoConsulta;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<IntegracionCarteraDTO> result;
  
 	public ObtenerEstadoCartera (String numeroIdentificacionAportante,String numeroIdentificacionDependiente,String periodoConsulta,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.numeroIdentificacionAportante=numeroIdentificacionAportante;
		this.numeroIdentificacionDependiente=numeroIdentificacionDependiente;
		this.periodoConsulta=periodoConsulta;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdentificacionAportante", numeroIdentificacionAportante)
						.queryParam("numeroIdentificacionDependiente", numeroIdentificacionDependiente)
						.queryParam("periodoConsulta", periodoConsulta)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<IntegracionCarteraDTO>) response.readEntity(new GenericType<List<IntegracionCarteraDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<IntegracionCarteraDTO> getResult() {
		return result;
	}

 
  	public void setNumeroIdentificacionAportante (String numeroIdentificacionAportante){
 		this.numeroIdentificacionAportante=numeroIdentificacionAportante;
 	}
 	
 	public String getNumeroIdentificacionAportante (){
 		return numeroIdentificacionAportante;
 	}
  	public void setNumeroIdentificacionDependiente (String numeroIdentificacionDependiente){
 		this.numeroIdentificacionDependiente=numeroIdentificacionDependiente;
 	}
 	
 	public String getNumeroIdentificacionDependiente (){
 		return numeroIdentificacionDependiente;
 	}
  	public void setPeriodoConsulta (String periodoConsulta){
 		this.periodoConsulta=periodoConsulta;
 	}
 	
 	public String getPeriodoConsulta (){
 		return periodoConsulta;
 	}
  	public void setTipoIdentificacion (TipoIdentificacionEnum tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  
}