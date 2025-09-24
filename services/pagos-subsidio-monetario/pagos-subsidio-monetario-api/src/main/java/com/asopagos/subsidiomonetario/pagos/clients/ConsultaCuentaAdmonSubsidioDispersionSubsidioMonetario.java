package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/ConsultaCuentaAdmonSubsidioDispersionSubsidioMonetario
 */
public class ConsultaCuentaAdmonSubsidioDispersionSubsidioMonetario extends ServiceClient {
	private Long idSolicitud;
	private String numeroTarjetaAdmonSubsidio;

	/** Atributo que almacena los datos resultado del llamado al servicio */
	private Long result;

	public ConsultaCuentaAdmonSubsidioDispersionSubsidioMonetario (Long idSolicitud, String numeroTarjetaAdmonSubsidio){
		super();
		this.idSolicitud = idSolicitud;
		this.numeroTarjetaAdmonSubsidio = numeroTarjetaAdmonSubsidio;
	}

	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
				.queryParam("idSolicitud", idSolicitud)
				.queryParam("numeroTarjetaAdmonSubsidio", numeroTarjetaAdmonSubsidio)
				.request(MediaType.APPLICATION_JSON)
				.post(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}

	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

	public Long getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getNumeroTarjetaAdmonSubsidio() {
		return numeroTarjetaAdmonSubsidio;
	}

	public void setNumeroTarjetaAdmonSubsidio(String numeroTarjetaAdmonSubsidio) {
		this.numeroTarjetaAdmonSubsidio = numeroTarjetaAdmonSubsidio;
	}
}