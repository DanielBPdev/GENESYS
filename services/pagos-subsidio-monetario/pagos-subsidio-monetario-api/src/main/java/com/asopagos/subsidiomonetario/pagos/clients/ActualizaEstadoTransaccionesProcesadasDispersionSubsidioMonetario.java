package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/ActualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario
 */
public class ActualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario extends ServiceClient {
	private Long idSolicitud;

	public ActualizaEstadoTransaccionesProcesadasDispersionSubsidioMonetario (Long idSolicitud){
		super();
		this.idSolicitud = idSolicitud;
	}

	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
				.queryParam("idSolicitud", idSolicitud)
				.request(MediaType.APPLICATION_JSON)
				.post(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {}

	public void setIdSolicitud (Long idSolicitud){
		this.idSolicitud=idSolicitud;
	}

	public Long getIdSolicitud (){
		return idSolicitud;
	}

}