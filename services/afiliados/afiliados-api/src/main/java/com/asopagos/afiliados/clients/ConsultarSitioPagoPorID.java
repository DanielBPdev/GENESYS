package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.SitioPagoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarSitioPagoPorID/{idSitioPago}
 */
public class ConsultarSitioPagoPorID extends ServiceClient {
 
  	private Long idSitioPago;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SitioPagoModeloDTO result;
  
 	public ConsultarSitioPagoPorID (Long idSitioPago){
 		super();
		this.idSitioPago=idSitioPago;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSitioPago", idSitioPago)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SitioPagoModeloDTO) response.readEntity(SitioPagoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SitioPagoModeloDTO getResult() {
		return result;
	}

 	public void setIdSitioPago (Long idSitioPago){
 		this.idSitioPago=idSitioPago;
 	}
 	
 	public Long getIdSitioPago (){
 		return idSitioPago;
 	}
  
  
}