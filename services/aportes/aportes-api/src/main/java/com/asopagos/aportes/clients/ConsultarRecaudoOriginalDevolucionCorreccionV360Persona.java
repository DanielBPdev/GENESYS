package com.asopagos.aportes.clients;

import com.asopagos.aportes.dto.CuentaAporteDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarRecaudoOriginalDevolucionCorreccionV360Persona
 */
public class ConsultarRecaudoOriginalDevolucionCorreccionV360Persona extends ServiceClient {
 
  
  	private Long idAporteDetallado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CuentaAporteDTO result;
  
 	public ConsultarRecaudoOriginalDevolucionCorreccionV360Persona (Long idAporteDetallado){
 		super();
		this.idAporteDetallado=idAporteDetallado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idAporteDetallado", idAporteDetallado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CuentaAporteDTO) response.readEntity(CuentaAporteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CuentaAporteDTO getResult() {
		return result;
	}

 
  	public void setIdAporteDetallado (Long idAporteDetallado){
 		this.idAporteDetallado=idAporteDetallado;
 	}
 	
 	public Long getIdAporteDetallado (){
 		return idAporteDetallado;
 	}
  
}