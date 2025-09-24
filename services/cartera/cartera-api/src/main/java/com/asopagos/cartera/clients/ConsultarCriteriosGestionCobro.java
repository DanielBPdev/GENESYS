package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.TipoGestionCarteraEnum;
import com.asopagos.enumeraciones.cartera.MetodoAccionCobroEnum;
import com.asopagos.dto.modelo.ParametrizacionCriteriosGestionCobroModeloDTO;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/{lineaCobro}/consultarCriteriosGestionCobro
 */
public class ConsultarCriteriosGestionCobro extends ServiceClient { 
  	private TipoLineaCobroEnum lineaCobro;
   	private TipoGestionCarteraEnum accion;
  	private MetodoAccionCobroEnum metodo;
   
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ParametrizacionCriteriosGestionCobroModeloDTO result;
  
 	public ConsultarCriteriosGestionCobro (TipoLineaCobroEnum lineaCobro,TipoGestionCarteraEnum accion,MetodoAccionCobroEnum metodo){
 		super();
		this.lineaCobro=lineaCobro;
		this.accion=accion;
		this.metodo=metodo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("lineaCobro", lineaCobro)
			.queryParam("accion", accion)
			.queryParam("metodo", metodo)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ParametrizacionCriteriosGestionCobroModeloDTO) response.readEntity(ParametrizacionCriteriosGestionCobroModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ParametrizacionCriteriosGestionCobroModeloDTO getResult() {
		return result;
	}

 	public void setLineaCobro (TipoLineaCobroEnum lineaCobro){
 		this.lineaCobro=lineaCobro;
 	}
 	
 	public TipoLineaCobroEnum getLineaCobro (){
 		return lineaCobro;
 	}
  
  	public void setAccion (TipoGestionCarteraEnum accion){
 		this.accion=accion;
 	}
 	
 	public TipoGestionCarteraEnum getAccion (){
 		return accion;
 	}
  	public void setMetodo (MetodoAccionCobroEnum metodo){
 		this.metodo=metodo;
 	}
 	
 	public MetodoAccionCobroEnum getMetodo (){
 		return metodo;
 	}
  
  
}