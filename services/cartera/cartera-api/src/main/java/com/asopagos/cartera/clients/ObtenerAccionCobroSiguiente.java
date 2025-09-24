package com.asopagos.cartera.clients;

import com.asopagos.enumeraciones.cartera.TipoAccionCobroEnum;
import com.asopagos.dto.modelo.CarteraModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/obtenerAccionCobroSiguiente
 */
public class ObtenerAccionCobroSiguiente extends ServiceClient { 
    	private CarteraModeloDTO carteraModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private TipoAccionCobroEnum result;
  
 	public ObtenerAccionCobroSiguiente (CarteraModeloDTO carteraModeloDTO){
 		super();
		this.carteraModeloDTO=carteraModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(carteraModeloDTO == null ? null : Entity.json(carteraModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (TipoAccionCobroEnum) response.readEntity(TipoAccionCobroEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public TipoAccionCobroEnum getResult() {
		return result;
	}

 
  
  	public void setCarteraModeloDTO (CarteraModeloDTO carteraModeloDTO){
 		this.carteraModeloDTO=carteraModeloDTO;
 	}
 	
 	public CarteraModeloDTO getCarteraModeloDTO (){
 		return carteraModeloDTO;
 	}
  
}