package com.asopagos.cartera.clients;

import java.util.List;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
import com.asopagos.dto.modelo.ParametrizacionCriteriosGestionCobroModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/guardarCriteriosGestionCobro
 */
public class GuardarCriteriosGestionCobro extends ServiceClient { 
   	private MetodoAccionCobroEnum metodo;
   	private List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizacionCriterios;
  
  
 	public GuardarCriteriosGestionCobro (MetodoAccionCobroEnum metodo,List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizacionCriterios){
 		super();
		this.metodo=metodo;
		this.parametrizacionCriterios=parametrizacionCriterios;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("metodo", metodo)
			.request(MediaType.APPLICATION_JSON)
			.post(parametrizacionCriterios == null ? null : Entity.json(parametrizacionCriterios));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  	public void setMetodo (MetodoAccionCobroEnum metodo){
 		this.metodo=metodo;
 	}
 	
 	public MetodoAccionCobroEnum getMetodo (){
 		return metodo;
 	}
  
  	public void setParametrizacionCriterios (List<ParametrizacionCriteriosGestionCobroModeloDTO> parametrizacionCriterios){
 		this.parametrizacionCriterios=parametrizacionCriterios;
 	}
 	
 	public List<ParametrizacionCriteriosGestionCobroModeloDTO> getParametrizacionCriterios (){
 		return parametrizacionCriterios;
 	}
  
}