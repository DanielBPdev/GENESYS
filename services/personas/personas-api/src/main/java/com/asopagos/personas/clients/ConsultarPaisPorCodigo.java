package com.asopagos.personas.clients;

import com.asopagos.dto.UbicacionDTO;
import com.asopagos.personas.dto.PaisDTO;
import com.asopagos.services.common.ServiceClient;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Metodo que hace la peticion REST al servicio GET
 * rest/personas/consultarPaisPorCodigo/{codigoPais}
 */
public class ConsultarPaisPorCodigo extends ServiceClient {

	private Long codigoPais;


	/** Atributo que almacena los datos resultado del llamado al servicio */
	private PaisDTO result;

	public ConsultarPaisPorCodigo(Long codigoPais){
		super();
		this.codigoPais=codigoPais;
	}

	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
				.resolveTemplate("codigoPais", codigoPais)
				.request(MediaType.APPLICATION_JSON).get();
		return response;
	}


	@Override
	protected void getResultData(Response response) {
		this.result = (PaisDTO) response.readEntity(PaisDTO.class);
	}

	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public PaisDTO getResult() {
		return result;
	}

	public Long getCodigoPais() {
		return codigoPais;
	}

	public void setCodigoPais(Long codigoPais) {
		this.codigoPais = codigoPais;
	}
}