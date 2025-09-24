package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.SitioPagoModeloDTO;
import com.asopagos.afiliados.dto.RespuestaServicioInfraestructuraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/afiliados/actualizarSitioPago
 */
public class ActualizarSitioPago extends ServiceClient { 
    	private SitioPagoModeloDTO datosActualizados;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaServicioInfraestructuraDTO result;
  
 	public ActualizarSitioPago (SitioPagoModeloDTO datosActualizados){
 		super();
		this.datosActualizados=datosActualizados;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(datosActualizados == null ? null : Entity.json(datosActualizados));
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

 
  
  	public void setDatosActualizados (SitioPagoModeloDTO datosActualizados){
 		this.datosActualizados=datosActualizados;
 	}
 	
 	public SitioPagoModeloDTO getDatosActualizados (){
 		return datosActualizados;
 	}
  
}