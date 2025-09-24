package com.asopagos.fovis.clients;

import com.asopagos.dto.fovis.InformacionDocumentoActaAsignacionDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarDatosGenerarSoporteActaAsignacionPorSolicitudGlobal
 */
public class ConsultarDatosGenerarSoporteActaAsignacionPorSolicitudGlobal extends ServiceClient {
 
  
  	private Long idSolicitudGlobal;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InformacionDocumentoActaAsignacionDTO result;
  
 	public ConsultarDatosGenerarSoporteActaAsignacionPorSolicitudGlobal (Long idSolicitudGlobal){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitudGlobal", idSolicitudGlobal)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InformacionDocumentoActaAsignacionDTO) response.readEntity(InformacionDocumentoActaAsignacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InformacionDocumentoActaAsignacionDTO getResult() {
		return result;
	}

 
  	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
}