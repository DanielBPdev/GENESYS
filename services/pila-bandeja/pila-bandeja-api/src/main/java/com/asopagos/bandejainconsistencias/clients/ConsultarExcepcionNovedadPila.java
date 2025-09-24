package com.asopagos.bandejainconsistencias.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.ExcepcionNovedadPilaModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pilaBandeja/consultarExcepcionNovedadPila
 */
public class ConsultarExcepcionNovedadPila extends ServiceClient {
 
  
  	private Long idTempNovedad;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ExcepcionNovedadPilaModeloDTO> result;
  
 	public ConsultarExcepcionNovedadPila (Long idTempNovedad){
 		super();
		this.idTempNovedad=idTempNovedad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idTempNovedad", idTempNovedad)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ExcepcionNovedadPilaModeloDTO>) response.readEntity(new GenericType<List<ExcepcionNovedadPilaModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ExcepcionNovedadPilaModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdTempNovedad (Long idTempNovedad){
 		this.idTempNovedad=idTempNovedad;
 	}
 	
 	public Long getIdTempNovedad (){
 		return idTempNovedad;
 	}
  
}