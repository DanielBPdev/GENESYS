package com.asopagos.cartera.composite.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/carteraComposite/retomarAccionesCobro
 */
public class RetomarAccionesCobro extends ServiceClient { 
   	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
  	private Long idPersona;
   
  
 	public RetomarAccionesCobro (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,Long idPersona){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.idPersona=idPersona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoSolicitante", tipoSolicitante)
			.queryParam("idPersona", idPersona)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  
  
}