package com.asopagos.solicitudes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.RegistroEstadoAporteModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudes/{idSolicitud}/consultarTrazabilidad
 */
public class ConsultarTrazabilidad extends ServiceClient {
 
  	private Long idSolicitud;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RegistroEstadoAporteModeloDTO> result;
  
 	public ConsultarTrazabilidad (Long idSolicitud){
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
		this.result = (List<RegistroEstadoAporteModeloDTO>) response.readEntity(new GenericType<List<RegistroEstadoAporteModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RegistroEstadoAporteModeloDTO> getResult() {
		return result;
	}

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  
}