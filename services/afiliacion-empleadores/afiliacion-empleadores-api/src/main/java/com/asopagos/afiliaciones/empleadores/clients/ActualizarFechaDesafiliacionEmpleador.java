package com.asopagos.afiliaciones.empleadores.clients;

import java.lang.Long;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

public class ActualizarFechaDesafiliacionEmpleador extends ServiceClient { 

  	private Long idEmpleador;
  
 	public ActualizarFechaDesafiliacionEmpleador (Long idEmpleador){
 		super();
		this.idEmpleador=idEmpleador;

 	}
 	
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idEmpleador", idEmpleador)
			.request(MediaType.APPLICATION_JSON).get();
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	
    public Long getIdEmpleador() {
        return this.idEmpleador;
    }

    public void setIdEmpleador(Long idEmpleador) {
        this.idEmpleador = idEmpleador;
    }

}
