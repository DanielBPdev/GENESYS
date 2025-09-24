package com.asopagos.entidades.pagadoras.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.pagadoras.dto.SolicitudAsociacionPersonaEntidadPagadoraDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/entidadesPagadoras/consultarPersonasAsociadasEntidad
 */
public class ConsultarPersonasAsociadasEntidad extends ServiceClient {
 
  
  	private Long idEntidadPagadora;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudAsociacionPersonaEntidadPagadoraDTO> result;
  
 	public ConsultarPersonasAsociadasEntidad (Long idEntidadPagadora){
 		super();
		this.idEntidadPagadora=idEntidadPagadora;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idEntidadPagadora", idEntidadPagadora)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<SolicitudAsociacionPersonaEntidadPagadoraDTO>) response.readEntity(new GenericType<List<SolicitudAsociacionPersonaEntidadPagadoraDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SolicitudAsociacionPersonaEntidadPagadoraDTO> getResult() {
		return result;
	}

 
  	public void setIdEntidadPagadora (Long idEntidadPagadora){
 		this.idEntidadPagadora=idEntidadPagadora;
 	}
 	
 	public Long getIdEntidadPagadora (){
 		return idEntidadPagadora;
 	}
  
}