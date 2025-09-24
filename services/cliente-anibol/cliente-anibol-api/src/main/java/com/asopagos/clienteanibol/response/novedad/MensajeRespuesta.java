package com.asopagos.clienteanibol.response.novedad;

import java.util.List;

public class MensajeRespuesta {

	private List<String> respuesta = null;
	private String id_proceso;

	public List<String> getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(List<String> respuesta) {
		this.respuesta = respuesta;
	}

	public String getId_proceso() {
		return id_proceso;
	}

	public void setId_proceso(String idProceso) {
		this.id_proceso = idProceso;
	}

}