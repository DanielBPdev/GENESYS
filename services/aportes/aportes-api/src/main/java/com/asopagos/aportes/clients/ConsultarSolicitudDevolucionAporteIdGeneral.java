package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.SolicitudDevolucionAporteModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarSolicitudDevolucionAporteIdGeneral
 */
public class ConsultarSolicitudDevolucionAporteIdGeneral extends ServiceClient {
 
  
  	private Long idAporteGeneral;
  	private Long idMovimientoAporte;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudDevolucionAporteModeloDTO result;
  
 	public ConsultarSolicitudDevolucionAporteIdGeneral (Long idAporteGeneral,Long idMovimientoAporte){
 		super();
		this.idAporteGeneral=idAporteGeneral;
		this.idMovimientoAporte=idMovimientoAporte;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idAporteGeneral", idAporteGeneral)
						.queryParam("idMovimientoAporte", idMovimientoAporte)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudDevolucionAporteModeloDTO) response.readEntity(SolicitudDevolucionAporteModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudDevolucionAporteModeloDTO getResult() {
		return result;
	}

 
  	public void setIdAporteGeneral (Long idAporteGeneral){
 		this.idAporteGeneral=idAporteGeneral;
 	}
 	
 	public Long getIdAporteGeneral (){
 		return idAporteGeneral;
 	}
  	public void setIdMovimientoAporte (Long idMovimientoAporte){
 		this.idMovimientoAporte=idMovimientoAporte;
 	}
 	
 	public Long getIdMovimientoAporte (){
 		return idMovimientoAporte;
 	}
  
}