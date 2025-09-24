package com.asopagos.aportes.composite.clients;

import java.lang.Long;
import javax.ws.rs.core.GenericType;
import com.asopagos.dto.aportes.CotizanteDTO;
import com.asopagos.enumeraciones.aportes.ModalidadRecaudoAporteEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aporteManual/gestionarCotizantes/{modalidadRecaudo}
 */
public class GestionarCotizantes extends ServiceClient { 
  	private ModalidadRecaudoAporteEnum modalidadRecaudo;
   	private Long idPlanilla;
   	private List<CotizanteDTO> cotizanteDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CotizanteDTO> result;
  
 	public GestionarCotizantes (ModalidadRecaudoAporteEnum modalidadRecaudo,Long idPlanilla,List<CotizanteDTO> cotizanteDTO){
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
		result = (List<CotizanteDTO>) response.readEntity(new GenericType<List<CotizanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<CotizanteDTO> getResult() {
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
  
  	public void setCotizanteDTO (List<CotizanteDTO> cotizanteDTO){
 		this.cotizanteDTO=cotizanteDTO;
 	}
 	
 	public List<CotizanteDTO> getCotizanteDTO (){
 		return cotizanteDTO;
 	}
  
}