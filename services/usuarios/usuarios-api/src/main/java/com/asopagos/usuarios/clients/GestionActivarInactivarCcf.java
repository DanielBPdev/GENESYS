package com.asopagos.usuarios.clients;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

public class GestionActivarInactivarCcf extends ServiceClient {

	private String nombreUsuario;
	private Boolean isInmediate;
	private Boolean activar; 

	public GestionActivarInactivarCcf(String nombreUsuario, Boolean isInmediate, Boolean activar) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.isInmediate = isInmediate;
		this.activar = activar;  
	}

	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
				.resolveTemplate("nombreUsuario", nombreUsuario)
				.queryParam("isInmediate", isInmediate)
				.queryParam("activar", activar)  
				.request(MediaType.APPLICATION_JSON)
				.post(null);
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setIsInmediate(Boolean isInmediate) {
		this.isInmediate = isInmediate;
	}

	public Boolean getIsInmediate() {
		return isInmediate;
	}
	
	public Boolean getActivar() {
		return activar;
	}

	public void setActivar(Boolean activar) {
		this.activar = activar;
	}
}
