package com.asopagos.fovis.clients;

import com.asopagos.dto.modelo.SolicitudPostulacionModeloDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/fovis/consultarPostulacionesCrucesInternos
 */
public class ConsultarPostulacionesCrucesInternos extends ServiceClient { 
   	private Long idCicloAsignacion;
   	private List<EstadoHogarEnum> estadosHogar;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudPostulacionModeloDTO> result;
  
 	public ConsultarPostulacionesCrucesInternos (Long idCicloAsignacion,List<EstadoHogarEnum> estadosHogar){
 		super();
		this.idCicloAsignacion=idCicloAsignacion;
		this.estadosHogar=estadosHogar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("idCicloAsignacion", idCicloAsignacion)
			.request(MediaType.APPLICATION_JSON)
			.post(estadosHogar == null ? null : Entity.json(estadosHogar));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<SolicitudPostulacionModeloDTO>) response.readEntity(new GenericType<List<SolicitudPostulacionModeloDTO>>(){});
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
  
  	public void setEstadosHogar (List<EstadoHogarEnum> estadosHogar){
 		this.estadosHogar=estadosHogar;
 	}
 	
 	public List<EstadoHogarEnum> getEstadosHogar (){
 		return estadosHogar;
 	}
  
}