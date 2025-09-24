package com.asopagos.aportes.clients;

import com.asopagos.dto.modelo.RegistroGeneralModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/{idSolicitud}/consultarRegistroGeneralLimitado
 */
public class ConsultarRegistroGeneralLimitado extends ServiceClient {
 
  	private Long idSolicitud;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RegistroGeneralModeloDTO result;
  
 	public ConsultarRegistroGeneralLimitado (Long idSolicitud){
 		super();
		this.idSolicitud=idSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitud", idSolicitud)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RegistroGeneralModeloDTO) response.readEntity(RegistroGeneralModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RegistroGeneralModeloDTO getResult() {
		return result;
	}

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  
}