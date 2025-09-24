package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import com.asopagos.cartera.dto.IntegracionCarteraDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/cartera/obtenerEstadoCarteraDetalleOld
 */
public class ObtenerEstadoCarteraDetalleOld extends ServiceClient {
 
  
  	private TipoSolicitanteMovimientoAporteEnum tipoAportante;
  	private TipoIdentificacionEnum tipoID;
  	private String identificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<IntegracionCarteraDTO> result;
  
 	public ObtenerEstadoCarteraDetalleOld (TipoSolicitanteMovimientoAporteEnum tipoAportante,TipoIdentificacionEnum tipoID,String identificacion){
 		super();
		this.tipoAportante=tipoAportante;
		this.tipoID=tipoID;
		this.identificacion=identificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoAportante", tipoAportante)
						.queryParam("tipoID", tipoID)
						.queryParam("identificacion", identificacion)
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

 
  	public void setTipoAportante (TipoSolicitanteMovimientoAporteEnum tipoAportante){
 		this.tipoAportante=tipoAportante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoAportante (){
 		return tipoAportante;
 	}
  	public void setTipoID (TipoIdentificacionEnum tipoID){
 		this.tipoID=tipoID;
 	}
 	
 	public TipoIdentificacionEnum getTipoID (){
 		return tipoID;
 	}
  	public void setIdentificacion (String identificacion){
 		this.identificacion=identificacion;
 	}
 	
 	public String getIdentificacion (){
 		return identificacion;
 	}
  
}