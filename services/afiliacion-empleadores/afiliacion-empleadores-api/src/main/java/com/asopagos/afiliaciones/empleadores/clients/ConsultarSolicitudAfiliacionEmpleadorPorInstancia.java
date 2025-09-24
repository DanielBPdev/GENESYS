package com.asopagos.afiliaciones.empleadores.clients;

import com.asopagos.dto.SolicitudDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudAfiliacionEmpleador/numeroRadicado/{numeroRadicado}
 */
public class ConsultarSolicitudAfiliacionEmpleadorPorInstancia extends ServiceClient {
 
  	private String instanciaProceso;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudDTO result;
  
 	public ConsultarSolicitudAfiliacionEmpleadorPorInstancia (String instanciaProceso){
 		super();
		this.instanciaProceso=instanciaProceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.queryParam("instanciaProceso", instanciaProceso)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudDTO) response.readEntity(SolicitudDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudDTO getResult() {
		return result;
	}


	public String getInstanciaProceso() {
		return this.instanciaProceso;
	}

	public void setInstanciaProceso(String instanciaProceso) {
		this.instanciaProceso = instanciaProceso;
	}
	public void setResult(SolicitudDTO result) {
		this.result = result;
	}

  
}