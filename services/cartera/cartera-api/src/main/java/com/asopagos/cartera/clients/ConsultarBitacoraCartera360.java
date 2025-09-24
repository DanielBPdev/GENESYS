package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.cartera.dto.BitacoraCarteraDTO;
import com.asopagos.dto.modelo.CarteraModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarBitacoraCartera360
 */
public class ConsultarBitacoraCartera360 extends ServiceClient { 
    	private CarteraModeloDTO carteraDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BitacoraCarteraDTO> result;
  
 	public ConsultarBitacoraCartera360 (CarteraModeloDTO carteraDTO){
 		super();
		this.carteraDTO=carteraDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(carteraDTO == null ? null : Entity.json(carteraDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<BitacoraCarteraDTO>) response.readEntity(new GenericType<List<BitacoraCarteraDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<BitacoraCarteraDTO> getResult() {
		return result;
	}

 
  
  	public void setCarteraDTO (CarteraModeloDTO carteraDTO){
 		this.carteraDTO=carteraDTO;
 	}
 	
 	public CarteraModeloDTO getCarteraDTO (){
 		return carteraDTO;
 	}
  
}