package com.asopagos.empleadores.clients;

import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalSolicitud;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empleadores/consultarInfoTemporalEmpleador
 */
public class ConsultarInfoTemporalEmpleador extends ServiceClient {
 
  
  	private Long idSolicitudGlobal;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatoTemporalSolicitud result;
  
 	public ConsultarInfoTemporalEmpleador (Long idSolicitudGlobal){
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
		this.result = (DatoTemporalSolicitud) response.readEntity(DatoTemporalSolicitud.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DatoTemporalSolicitud getResult() {
		return result;
	}

 
  	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
}