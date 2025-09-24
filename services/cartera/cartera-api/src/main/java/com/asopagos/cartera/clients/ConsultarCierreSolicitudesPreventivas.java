package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.SolicitudPreventivaAgrupadoraModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarCierreSolicitudesPreventivas
 */
public class ConsultarCierreSolicitudesPreventivas extends ServiceClient {
 
  
  	private List<Long> idSolicitudAgrupadora;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudPreventivaAgrupadoraModeloDTO> result;
  
 	public ConsultarCierreSolicitudesPreventivas (List<Long> idSolicitudAgrupadora){
 		super();
		this.idSolicitudAgrupadora=idSolicitudAgrupadora;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idSolicitudAgrupadora", idSolicitudAgrupadora.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudPreventivaAgrupadoraModeloDTO>) response.readEntity(new GenericType<List<SolicitudPreventivaAgrupadoraModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudPreventivaAgrupadoraModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdSolicitudAgrupadora (List<Long> idSolicitudAgrupadora){
 		this.idSolicitudAgrupadora=idSolicitudAgrupadora;
 	}
 	
 	public List<Long> getIdSolicitudAgrupadora (){
 		return idSolicitudAgrupadora;
 	}
  
}