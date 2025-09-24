package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.SitioPagoModeloDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarSitioPagoPorID/{idSitioPago}
 */
public class ConsultarIdSitioPagoPredeterminado extends ServiceClient {

	/** Atributo que almacena los datos resultado del llamado al servicio */
	private Long result;

	public ConsultarIdSitioPagoPredeterminado(){
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
		this.result = (Long) response.readEntity(Long.class);
	}

	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

}