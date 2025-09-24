package com.asopagos.aportes.composite.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.aportes.composite.dto.DatosCotizanteDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/consultarDatosCotizanteCargue
 */
public class ConsultarDatosCotizanteCargue extends ServiceClient { 
   	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
   	private List<DatosCotizanteDTO> datosCotizante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DatosCotizanteDTO> result;
  
 	public ConsultarDatosCotizanteCargue (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,List<DatosCotizanteDTO> datosCotizante){
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
		result = (List<DatosCotizanteDTO>) response.readEntity(new GenericType<List<DatosCotizanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<DatosCotizanteDTO> getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  
  	public void setDatosCotizante (List<DatosCotizanteDTO> datosCotizante){
 		this.datosCotizante=datosCotizante;
 	}
 	
 	public List<DatosCotizanteDTO> getDatosCotizante (){
 		return datosCotizante;
 	}
  
}