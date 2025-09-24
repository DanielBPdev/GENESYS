package com.asopagos.fovis.clients;

import java.util.List;


import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/ConsultarPersonaPerdioViviendaImposibilidadPago
 */
public class ConsultarPersonaPerdioViviendaImposibilidadPago extends ServiceClient {
 
  
	private TipoIdentificacionEnum tipoIdentificacion;
  	private String numeroIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Integer> result;
 
 	public ConsultarPersonaPerdioViviendaImposibilidadPago(TipoIdentificacionEnum tipoIdentificacion,String numeroIdentificacion ) {
		super();
		this.tipoIdentificacion=tipoIdentificacion;
		this.numeroIdentificacion=numeroIdentificacion;
		
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
		this.result = (List<Integer>) response.readEntity(new GenericType<List<Integer>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Integer> getResult() {
		return result;
	}

	public TipoIdentificacionEnum getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacionEnum tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

  
}