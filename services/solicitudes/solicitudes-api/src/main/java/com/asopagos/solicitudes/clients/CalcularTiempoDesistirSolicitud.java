package com.asopagos.solicitudes.clients;

import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudes/{idInstanciaProceso}/destinatario
 */
public class CalcularTiempoDesistirSolicitud extends ServiceClient { 
	private Long idSolicitud;
	private String idTarea;
   	private String estadoSolicitud;
	private String tipoTransaccion;   

  
 	public CalcularTiempoDesistirSolicitud (Long idSolicitud, String idTarea,  String estadoSolicitud, String tipoTransaccion){
 		super();
		this.idSolicitud=idSolicitud;
		this.idTarea=idTarea;
		this.estadoSolicitud=estadoSolicitud;
		this.tipoTransaccion=tipoTransaccion;

 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idSolicitud", this.idSolicitud)
			.queryParam("idTarea", this.idTarea)
			.queryParam("estadoSolicitud", this.estadoSolicitud)
			.queryParam("tipoTransaccion", this.tipoTransaccion)
			.request(MediaType.APPLICATION_JSON)
			.post(null);
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

	public String getIdTarea() {
		return this.idTarea;
	}

	public void setIdTarea(String idTarea) {
		this.idTarea = idTarea;
	}

	public Long getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public String getEstadoSolicitud() {
		return this.estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public String getTipoTransaccion() {
		return this.tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
  
}