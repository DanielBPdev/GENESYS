package com.asopagos.aportes.clients;

import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.dto.modelo.IndicePlanillaModeloDTO;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/ConsultarIndicePlanillaNumeroAportante/{idPlanilla}
 */
public class ConsultarIndicePlanillaNumeroAportante extends ServiceClient { 
  	private Long idPlanilla;
   	private Long registroDetallado;
    private IndicePlanillaModeloDTO result;
   
  
 	public ConsultarIndicePlanillaNumeroAportante (Long idPlanilla,Long registroDetallado){
 		super();
		this.idPlanilla=idPlanilla;
		this.registroDetallado=registroDetallado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
        .resolveTemplate("idPlanilla", idPlanilla)
                    .queryParam("registroDetallado", registroDetallado)
        .request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		this.result = (IndicePlanillaModeloDTO) response.readEntity(IndicePlanillaModeloDTO.class);
	}
    public IndicePlanillaModeloDTO getResult() {
		return result;
	}
	

    public Long getIdPlanilla() {
        return this.idPlanilla;
    }

    public void setIdPlanilla(Long idPlanilla) {
        this.idPlanilla = idPlanilla;
    }



	public Long getRegistroDetallado() {
		return this.registroDetallado;
	}

	public void setRegistroDetallado(Long registroDetallado) {
		this.registroDetallado = registroDetallado;
	}

  
  
}