package com.asopagos.cartera.clients;

import java.math.BigDecimal;
import java.util.List;
import com.asopagos.dto.modelo.PersonaModeloDTO;
import com.asopagos.enumeraciones.aportes.TipoSolicitanteMovimientoAporteEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarPromedioPeriodoImpago
 */
public class ConsultarPromedioPeriodoImpago extends ServiceClient { 
   	private TipoSolicitanteMovimientoAporteEnum tipoSolicitante;
   	private List<PersonaModeloDTO> personaModeloDTOs;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private BigDecimal result;
  
 	public ConsultarPromedioPeriodoImpago (TipoSolicitanteMovimientoAporteEnum tipoSolicitante,List<PersonaModeloDTO> personaModeloDTOs){
 		super();
		this.tipoSolicitante=tipoSolicitante;
		this.personaModeloDTOs=personaModeloDTOs;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoSolicitante", tipoSolicitante)
			.request(MediaType.APPLICATION_JSON)
			.post(personaModeloDTOs == null ? null : Entity.json(personaModeloDTOs));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (BigDecimal) response.readEntity(BigDecimal.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public BigDecimal getResult() {
		return result;
	}

 
  	public void setTipoSolicitante (TipoSolicitanteMovimientoAporteEnum tipoSolicitante){
 		this.tipoSolicitante=tipoSolicitante;
 	}
 	
 	public TipoSolicitanteMovimientoAporteEnum getTipoSolicitante (){
 		return tipoSolicitante;
 	}
  
  	public void setPersonaModeloDTOs (List<PersonaModeloDTO> personaModeloDTOs){
 		this.personaModeloDTOs=personaModeloDTOs;
 	}
 	
 	public List<PersonaModeloDTO> getPersonaModeloDTOs (){
 		return personaModeloDTOs;
 	}
  
}