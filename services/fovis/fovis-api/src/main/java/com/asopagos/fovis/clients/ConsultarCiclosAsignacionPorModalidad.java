package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import com.asopagos.dto.modelo.CicloAsignacionModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarCiclosAsignacionPorModalidad
 */
public class ConsultarCiclosAsignacionPorModalidad extends ServiceClient {
 
  
  	private ModalidadEnum modalidad;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CicloAsignacionModeloDTO> result;
  
 	public ConsultarCiclosAsignacionPorModalidad (ModalidadEnum modalidad){
 		super();
		this.modalidad=modalidad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("modalidad", modalidad)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CicloAsignacionModeloDTO>) response.readEntity(new GenericType<List<CicloAsignacionModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CicloAsignacionModeloDTO> getResult() {
		return result;
	}

 
  	public void setModalidad (ModalidadEnum modalidad){
 		this.modalidad=modalidad;
 	}
 	
 	public ModalidadEnum getModalidad (){
 		return modalidad;
 	}
  
}