package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/obtenerPostulacionesParaControlInterno
 */
public class ObtenerPostulacionesParaControlInterno extends ServiceClient {
 
  
  	private Long idCicloAsignacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudPostulacionModeloDTO> result;
  
 	public ObtenerPostulacionesParaControlInterno (Long idCicloAsignacion){
 		super();
		this.idCicloAsignacion=idCicloAsignacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCicloAsignacion", idCicloAsignacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudPostulacionModeloDTO>) response.readEntity(new GenericType<List<SolicitudPostulacionModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudPostulacionModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdCicloAsignacion (Long idCicloAsignacion){
 		this.idCicloAsignacion=idCicloAsignacion;
 	}
 	
 	public Long getIdCicloAsignacion (){
 		return idCicloAsignacion;
 	}
  
}