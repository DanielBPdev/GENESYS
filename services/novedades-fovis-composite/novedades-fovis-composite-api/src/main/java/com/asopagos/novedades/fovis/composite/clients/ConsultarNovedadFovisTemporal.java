package com.asopagos.novedades.fovis.composite.clients;

import java.lang.Long;
import com.asopagos.novedades.fovis.composite.dto.SolicitudNovedadFovisDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedadesFovisComposite/consultarNovedadFovisTemporal/{idSolicitudGlobal}
 */
public class ConsultarNovedadFovisTemporal extends ServiceClient {
 
  	private Long idSolicitudGlobal;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudNovedadFovisDTO result;
  
 	public ConsultarNovedadFovisTemporal (Long idSolicitudGlobal){
 		super();
		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitudGlobal", idSolicitudGlobal)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudNovedadFovisDTO) response.readEntity(SolicitudNovedadFovisDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudNovedadFovisDTO getResult() {
		return result;
	}

 	public void setIdSolicitudGlobal (Long idSolicitudGlobal){
 		this.idSolicitudGlobal=idSolicitudGlobal;
 	}
 	
 	public Long getIdSolicitudGlobal (){
 		return idSolicitudGlobal;
 	}
  
  
}