package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.TipoLineaCobroEnum;
import com.asopagos.dto.modelo.LineaCobroPersonaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarLineaCobroPersona/{tipoLinea}
 */
public class ConsultarLineaCobroPersona extends ServiceClient { 
  	private TipoLineaCobroEnum tipoLinea;
    
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private LineaCobroPersonaModeloDTO result;
  
 	public ConsultarLineaCobroPersona (TipoLineaCobroEnum tipoLinea){
 		super();
		this.tipoLinea=tipoLinea;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("tipoLinea", tipoLinea)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (LineaCobroPersonaModeloDTO) response.readEntity(LineaCobroPersonaModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public LineaCobroPersonaModeloDTO getResult() {
		return result;
	}

 	public void setTipoLinea (TipoLineaCobroEnum tipoLinea){
 		this.tipoLinea=tipoLinea;
 	}
 	
 	public TipoLineaCobroEnum getTipoLinea (){
 		return tipoLinea;
 	}
  
  
  
}