package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.ParametrizacionDesafiliacionModeloDTO;
import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{lineaCobro}/consultarParametrizacionDesafiliacion
 */
public class ConsultarParametrizacionDesafiliacion extends ServiceClient {
 
  	private TipoLineaCobroEnum lineaCobro;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ParametrizacionDesafiliacionModeloDTO result;
  
 	public ConsultarParametrizacionDesafiliacion (TipoLineaCobroEnum lineaCobro){
 		super();
		this.lineaCobro=lineaCobro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("lineaCobro", lineaCobro)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ParametrizacionDesafiliacionModeloDTO) response.readEntity(ParametrizacionDesafiliacionModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ParametrizacionDesafiliacionModeloDTO getResult() {
		return result;
	}

 	public void setLineaCobro (TipoLineaCobroEnum lineaCobro){
 		this.lineaCobro=lineaCobro;
 	}
 	
 	public TipoLineaCobroEnum getLineaCobro (){
 		return lineaCobro;
 	}
  
  
}