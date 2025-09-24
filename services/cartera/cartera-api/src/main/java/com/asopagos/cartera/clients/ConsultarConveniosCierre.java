package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.util.Date;
import com.asopagos.dto.modelo.ConvenioPagoModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarConveniosCierre
 */
public class ConsultarConveniosCierre extends ServiceClient { 
    	private List<Date> diasFestivos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ConvenioPagoModeloDTO> result;
  
 	public ConsultarConveniosCierre (List<Date> diasFestivos){
 		super();
		this.diasFestivos=diasFestivos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(diasFestivos == null ? null : Entity.json(diasFestivos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ConvenioPagoModeloDTO>) response.readEntity(new GenericType<List<ConvenioPagoModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ConvenioPagoModeloDTO> getResult() {
		return result;
	}

 
  
  	public void setDiasFestivos (List<Date> diasFestivos){
 		this.diasFestivos=diasFestivos;
 	}
 	
 	public List<Date> getDiasFestivos (){
 		return diasFestivos;
 	}
  
}