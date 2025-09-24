package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.FirmezaDeTituloDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/ConsultarCarteraLineaCobro
 */
public class ConsultarCarteraLineaCobro extends ServiceClient {



  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<FirmezaDeTituloDTO> result;

 	public ConsultarCarteraLineaCobro (){
 		super();
 	}

 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}


	@Override
	protected void getResultData(Response response) {
		this.result = (List<FirmezaDeTituloDTO>) response.readEntity(new GenericType<List<FirmezaDeTituloDTO>>(){});
	}

	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<FirmezaDeTituloDTO> getResult() {
		return result;
	}



}