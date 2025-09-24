package com.asopagos.aportes.composite.clients;

import com.asopagos.aportes.composite.dto.DatosCotizanteDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/consultarDatosCotizante
 */
public class ConsultarDatosCotizante extends ServiceClient { 
   	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
   	private DatosCotizanteDTO datosCotizante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatosCotizanteDTO result;
  
 	public ConsultarDatosCotizante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,DatosCotizanteDTO datosCotizante){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.datosCotizante=datosCotizante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoSolicitante", tipoSolicitante)
			.request(MediaType.APPLICATION_JSON)
			.post(datosCotizante == null ? null : Entity.json(datosCotizante));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (DatosCotizanteDTO) response.readEntity(DatosCotizanteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DatosCotizanteDTO getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  
  	public void setDatosCotizante (DatosCotizanteDTO datosCotizante){
 		this.datosCotizante=datosCotizante;
 	}
 	
 	public DatosCotizanteDTO getDatosCotizante (){
 		return datosCotizante;
 	}
  
}