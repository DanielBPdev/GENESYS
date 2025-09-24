package com.asopagos.personas.clients;

import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.personas.dto.PaisDTO;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/personas/obtenerNombrePais/{idPais}
 */
public class ObtenerNombrePais extends ServiceClient {

	private Long idPais;

	/** Atributo que almacena los datos resultado del llamado al servicio */
	private PaisDTO result;

	public ObtenerNombrePais(Long idPais) {
		super();
		this.idPais = idPais;
	}

	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
				.resolveTemplate("idPais", idPais)
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

	public void setIdPais(Long idPais) {
		this.idPais = idPais;
	}

	public Long getIdPais() {
		return idPais;
	}

}