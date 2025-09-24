package com.asopagos.cartera.composite.clients;

import com.asopagos.enumeraciones.cartera.TipoGestionCarteraEnum;
import com.asopagos.dto.modelo.ParametrizacionCriteriosGestionCobroModeloDTO;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/carteraComposite/{lineaCobro}/consultarParametrizacionCriterioTemporal
 */
public class ConsultarParametrizacionCriterioTemporal extends ServiceClient {
 
  	private TipoLineaCobroEnum lineaCobro;
  
  	private TipoGestionCarteraEnum accion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ParametrizacionCriteriosGestionCobroModeloDTO result;
  
 	public ConsultarParametrizacionCriterioTemporal (TipoLineaCobroEnum lineaCobro,TipoGestionCarteraEnum accion){
 		super();
		this.lineaCobro=lineaCobro;
		this.accion=accion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("lineaCobro", lineaCobro)
									.queryParam("accion", accion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ParametrizacionCriteriosGestionCobroModeloDTO) response.readEntity(ParametrizacionCriteriosGestionCobroModeloDTO.class);
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
  
}