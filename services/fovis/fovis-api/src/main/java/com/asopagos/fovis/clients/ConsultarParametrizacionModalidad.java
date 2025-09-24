package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.ParametrizacionModalidadModeloDTO;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarParametrizacionModalidad/{nombreParametrizacionModalidad}
 */
public class ConsultarParametrizacionModalidad extends ServiceClient {
 
  	private ModalidadEnum nombreParametrizacionModalidad;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ParametrizacionModalidadModeloDTO result;
  
 	public ConsultarParametrizacionModalidad (ModalidadEnum nombreParametrizacionModalidad){
 		super();
		this.nombreParametrizacionModalidad=nombreParametrizacionModalidad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("nombreParametrizacionModalidad", nombreParametrizacionModalidad)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ParametrizacionModalidadModeloDTO) response.readEntity(ParametrizacionModalidadModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ParametrizacionModalidadModeloDTO getResult() {
		return result;
	}

 	public void setNombreParametrizacionModalidad (ModalidadEnum nombreParametrizacionModalidad){
 		this.nombreParametrizacionModalidad=nombreParametrizacionModalidad;
 	}
 	
 	public ModalidadEnum getNombreParametrizacionModalidad (){
 		return nombreParametrizacionModalidad;
 	}
  
  
}