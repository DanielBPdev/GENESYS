package com.asopagos.novedades.fovis.clients;

import com.asopagos.dto.modelo.ActoAceptacionProrrogaFovisModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedadesFovis/consultarActoAceptacionProrrogaFovisPorNovedadFovis
 */
public class ConsultarActoAceptacionProrrogaFovisPorNovedadFovis extends ServiceClient {
 
  
  	private Long idSolicitudNovedadFovis;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ActoAceptacionProrrogaFovisModeloDTO result;
  
 	public ConsultarActoAceptacionProrrogaFovisPorNovedadFovis (Long idSolicitudNovedadFovis){
 		super();
		this.idSolicitudNovedadFovis=idSolicitudNovedadFovis;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitudNovedadFovis", idSolicitudNovedadFovis)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ActoAceptacionProrrogaFovisModeloDTO) response.readEntity(ActoAceptacionProrrogaFovisModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ActoAceptacionProrrogaFovisModeloDTO getResult() {
		return result;
	}

 
  	public void setIdSolicitudNovedadFovis (Long idSolicitudNovedadFovis){
 		this.idSolicitudNovedadFovis=idSolicitudNovedadFovis;
 	}
 	
 	public Long getIdSolicitudNovedadFovis (){
 		return idSolicitudNovedadFovis;
 	}
  
}