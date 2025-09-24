package com.asopagos.cartera.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.dto.modelo.DocumentoCarteraModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarDocumentoCartera
 */
public class ConsultarDocumentoCartera extends ServiceClient {
 
  
  	private Long numeroOperacion;
  	private TipoAccionCobroEnum accionCobro;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DocumentoCarteraModeloDTO result;
  
 	public ConsultarDocumentoCartera (Long numeroOperacion,TipoAccionCobroEnum accionCobro){
 		super();
		this.numeroOperacion=numeroOperacion;
		this.accionCobro=accionCobro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroOperacion", numeroOperacion)
						.queryParam("accionCobro", accionCobro)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DocumentoCarteraModeloDTO) response.readEntity(DocumentoCarteraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DocumentoCarteraModeloDTO getResult() {
		return result;
	}

 
  	public void setNumeroOperacion (Long numeroOperacion){
 		this.numeroOperacion=numeroOperacion;
 	}
 	
 	public Long getNumeroOperacion (){
 		return numeroOperacion;
 	}
  	public void setAccionCobro (TipoAccionCobroEnum accionCobro){
 		this.accionCobro=accionCobro;
 	}
 	
 	public TipoAccionCobroEnum getAccionCobro (){
 		return accionCobro;
 	}
  
}