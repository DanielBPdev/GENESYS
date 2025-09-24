package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarHogaresAplicanCalificacionPostulacion
 */
public class ConsultarHogaresAplicanCalificacionPostulacion extends ServiceClient {
 
  
  	private Long idCicloAsignacion;

	private Boolean calificados;

  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PostulacionFOVISModeloDTO> result;
  
 	public ConsultarHogaresAplicanCalificacionPostulacion (Long idCicloAsignacion, Boolean calificados){
 		super();
		this.idCicloAsignacion=idCicloAsignacion;
		this.calificados=calificados;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idCicloAsignacion", idCicloAsignacion)
				.queryParam("calificados", calificados)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PostulacionFOVISModeloDTO>) response.readEntity(new GenericType<List<PostulacionFOVISModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PostulacionFOVISModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdCicloAsignacion (Long idCicloAsignacion){
 		this.idCicloAsignacion=idCicloAsignacion;
 	}
 	
 	public Long getIdCicloAsignacion (){
 		return idCicloAsignacion;
 	}

	public Boolean getCalificados() {
		return calificados;
	}

	public void setCalificados(Boolean calificados) {
		this.calificados = calificados;
	}
}