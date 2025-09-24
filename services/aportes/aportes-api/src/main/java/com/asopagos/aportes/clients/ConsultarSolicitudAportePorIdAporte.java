package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.SolicitudAporteModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarSolicitudAportePorIdAporte/{idAporteGeneral}
 */
public class ConsultarSolicitudAportePorIdAporte extends ServiceClient {
 
  	private Long idAporteGeneral;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAporteModeloDTO result;
  
 	public ConsultarSolicitudAportePorIdAporte (Long idAporteGeneral){
 		super();
		this.idAporteGeneral=idAporteGeneral;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idAporteGeneral", idAporteGeneral)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudAporteModeloDTO) response.readEntity(SolicitudAporteModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudAporteModeloDTO getResult() {
		return result;
	}

 	public void setIdAporteGeneral (Long idAporteGeneral){
 		this.idAporteGeneral=idAporteGeneral;
 	}
 	
 	public Long getIdAporteGeneral (){
 		return idAporteGeneral;
 	}
  
  
}