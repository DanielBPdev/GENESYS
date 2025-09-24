package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/gestionarCotizante/{modalidadRecaudo}
 */
public class GestionarCotizante extends ServiceClient { 
  	private ModalidadRecaudoAporteEnum modalidadRecaudo;
   	private Long idPlanilla;
   	private CotizanteDTO cotizanteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CotizanteDTO result;
  
 	public GestionarCotizante (ModalidadRecaudoAporteEnum modalidadRecaudo,Long idPlanilla,CotizanteDTO cotizanteDTO){
 		super();
		this.modalidadRecaudo=modalidadRecaudo;
		this.idPlanilla=idPlanilla;
		this.cotizanteDTO=cotizanteDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("modalidadRecaudo", modalidadRecaudo)
			.queryParam("idPlanilla", idPlanilla)
			.request(MediaType.APPLICATION_JSON)
			.post(cotizanteDTO == null ? null : Entity.json(cotizanteDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (CotizanteDTO) response.readEntity(CotizanteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public CotizanteDTO getResult() {
		return result;
	}

 	public void setModalidadRecaudo (ModalidadRecaudoAporteEnum modalidadRecaudo){
 		this.modalidadRecaudo=modalidadRecaudo;
 	}
 	
 	public ModalidadRecaudoAporteEnum getModalidadRecaudo (){
 		return modalidadRecaudo;
 	}
  
  	public void setIdPlanilla (Long idPlanilla){
 		this.idPlanilla=idPlanilla;
 	}
 	
 	public Long getIdPlanilla (){
 		return idPlanilla;
 	}
  
  	public void setCotizanteDTO (CotizanteDTO cotizanteDTO){
 		this.cotizanteDTO=cotizanteDTO;
 	}
 	
 	public CotizanteDTO getCotizanteDTO (){
 		return cotizanteDTO;
 	}
  
}