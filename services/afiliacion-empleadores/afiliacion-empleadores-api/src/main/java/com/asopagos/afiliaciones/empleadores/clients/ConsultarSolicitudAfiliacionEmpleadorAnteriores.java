package com.asopagos.afiliaciones.empleadores.clients;

import java.lang.Long;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.util.List;
import com.asopagos.entidades.ccf.afiliaciones.SolicitudAfiliacionEmpleador;

import com.asopagos.services.common.ServiceClient;

public class ConsultarSolicitudAfiliacionEmpleadorAnteriores extends ServiceClient { 

  	private List<SolicitudAfiliacionEmpleador> result;

	private TipoIdentificacionEnum tipoIdentificacion;
	
	private String numeroIdentificacion;
  
 	public ConsultarSolicitudAfiliacionEmpleadorAnteriores (TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion){
 		super();

		this.tipoIdentificacion = tipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion;

 	}
 	
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoIdentificacion", tipoIdentificacion)
			.queryParam("numeroIdentificacion", numeroIdentificacion)
			.request(MediaType.APPLICATION_JSON).get();
		return response;
	}

	@Override
	protected void getResultData(Response response) {

		this.result = (List<SolicitudAfiliacionEmpleador>) response.readEntity(new GenericType<List<SolicitudAfiliacionEmpleador>>(){});
	}


	public List<SolicitudAfiliacionEmpleador> getResult() {
		return this.result;
	}
	

	public TipoIdentificacionEnum getTipoIdentificacion() {
		return this.tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return this.numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
    

}
