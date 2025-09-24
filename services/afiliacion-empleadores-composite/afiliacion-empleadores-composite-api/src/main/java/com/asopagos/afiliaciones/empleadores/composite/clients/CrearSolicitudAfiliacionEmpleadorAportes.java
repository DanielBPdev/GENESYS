package com.asopagos.afiliaciones.empleadores.composite.clients;

import java.lang.Long;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.dto.ActivacionEmpleadorDTO;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/crearSolicitudAfiliacionEmpleadorAportes
 */

public class CrearSolicitudAfiliacionEmpleadorAportes extends ServiceClient { 

  	/** Atributo que almacena los datos resultado del llamado al servicio */

	private ActivacionEmpleadorDTO datosEmpleador;
 	
  
 	public CrearSolicitudAfiliacionEmpleadorAportes (ActivacionEmpleadorDTO datosEmpleador){
 		super();
		this.datosEmpleador=datosEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosEmpleador == null ? null : Entity.json(datosEmpleador));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	


	public ActivacionEmpleadorDTO getDatosEmpleador() {
		return this.datosEmpleador;
	}

	public void setDatosEmpleador(ActivacionEmpleadorDTO datosEmpleador) {
		this.datosEmpleador = datosEmpleador;
	}
  
}