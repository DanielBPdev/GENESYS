package com.asopagos.consola.ejecucion.proceso.asincrono.clients;

import com.asopagos.dto.EjecucionProcesoAsincronoDTO;
import com.asopagos.enumeraciones.fovis.TipoProcesoAsincronoEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/consolaEjecucionProcesosAsincronos/consultarUltimaEjecucionProcesoAsincrono
 */
public class ConsultarUltimaEjecucionProcesoAsincrono extends ServiceClient {
 
  
  	private TipoProcesoAsincronoEnum tipoProceso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EjecucionProcesoAsincronoDTO result;
  
 	public ConsultarUltimaEjecucionProcesoAsincrono (TipoProcesoAsincronoEnum tipoProceso){
 		super();
		this.tipoProceso=tipoProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoProceso", tipoProceso)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EjecucionProcesoAsincronoDTO) response.readEntity(EjecucionProcesoAsincronoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EjecucionProcesoAsincronoDTO getResult() {
		return result;
	}

 
  	public void setTipoProceso (TipoProcesoAsincronoEnum tipoProceso){
 		this.tipoProceso=tipoProceso;
 	}
 	
 	public TipoProcesoAsincronoEnum getTipoProceso (){
 		return tipoProceso;
 	}
  
}