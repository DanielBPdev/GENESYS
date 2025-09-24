package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.afiliados.dto.CriteriosConsultaModeloInfraestructuraDTO;
import com.asopagos.afiliados.dto.PresentacionResultadoModeloInfraestructuraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliados/consultarSitiosPago
 */
public class ConsultarSitiosPago extends ServiceClient { 
    	private CriteriosConsultaModeloInfraestructuraDTO criterios;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PresentacionResultadoModeloInfraestructuraDTO> result;
  
 	public ConsultarSitiosPago (CriteriosConsultaModeloInfraestructuraDTO criterios){
 		super();
		this.criterios=criterios;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(criterios == null ? null : Entity.json(criterios));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
		result = (List<PresentacionResultadoModeloInfraestructuraDTO>) response.readEntity(new GenericType<List<PresentacionResultadoModeloInfraestructuraDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PresentacionResultadoModeloInfraestructuraDTO> getResult() {
		return result;
	}

 
  
  	public void setCriterios (CriteriosConsultaModeloInfraestructuraDTO criterios){
 		this.criterios=criterios;
 	}
 	
 	public CriteriosConsultaModeloInfraestructuraDTO getCriterios (){
 		return criterios;
 	}
  
}