package com.asopagos.cartera.composite.clients;

import java.util.Map;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/carteraComposite/obtenerParametrosComunicadoPreventiva
 */
public class ObtenerParametrosComunicadoPreventiva extends ServiceClient {
 
  
  	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private String numeroIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public ObtenerParametrosComunicadoPreventiva (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,String numeroIdentificacion){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.numeroIdentificacion=numeroIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoSolicitante", tipoSolicitante)
						.queryParam("numeroIdentificacion", numeroIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<String,String> getResult() {
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
  
}