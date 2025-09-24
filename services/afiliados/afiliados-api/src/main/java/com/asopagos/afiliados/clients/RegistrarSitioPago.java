package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.SitioPagoModeloDTO;
import com.asopagos.afiliados.dto.RespuestaServicioInfraestructuraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/registrarSitioPago
 */
public class RegistrarSitioPago extends ServiceClient { 
    	private SitioPagoModeloDTO sitioPago;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaServicioInfraestructuraDTO result;
  
 	public RegistrarSitioPago (SitioPagoModeloDTO sitioPago){
 		super();
		this.sitioPago=sitioPago;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(sitioPago == null ? null : Entity.json(sitioPago));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (RespuestaServicioInfraestructuraDTO) response.readEntity(RespuestaServicioInfraestructuraDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public RespuestaServicioInfraestructuraDTO getResult() {
		return result;
	}

 
  
  	public void setSitioPago (SitioPagoModeloDTO sitioPago){
 		this.sitioPago=sitioPago;
 	}
 	
 	public SitioPagoModeloDTO getSitioPago (){
 		return sitioPago;
 	}
  
}