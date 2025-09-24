package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.dto.cartera.ExclusionCarteraDTO;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarExclusionPorAportante
 */
public class ConsultarExclusionPorAportante extends ServiceClient {
 
  
  	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private String numeroIdentificacion;
  	private TipoIdentificacionEnum tipoIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ExclusionCarteraDTO> result;
  
 	public ConsultarExclusionPorAportante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,String numeroIdentificacion,TipoIdentificacionEnum tipoIdentificacion){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.numeroIdentificacion=numeroIdentificacion;
		this.tipoIdentificacion=tipoIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoSolicitante", tipoSolicitante)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ExclusionCarteraDTO>) response.readEntity(new GenericType<List<ExclusionCarteraDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ExclusionCarteraDTO> getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
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