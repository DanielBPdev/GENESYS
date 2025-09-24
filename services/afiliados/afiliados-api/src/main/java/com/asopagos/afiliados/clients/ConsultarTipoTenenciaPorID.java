package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.TipoTenenciaModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarTipoTenenciaPorID/{idTipoTenencia}
 */
public class ConsultarTipoTenenciaPorID extends ServiceClient {
 
  	private Long idTipoTenencia;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private TipoTenenciaModeloDTO result;
  
 	public ConsultarTipoTenenciaPorID (Long idTipoTenencia){
 		super();
		this.idTipoTenencia=idTipoTenencia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idTipoTenencia", idTipoTenencia)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (TipoTenenciaModeloDTO) response.readEntity(TipoTenenciaModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public TipoTenenciaModeloDTO getResult() {
		return result;
	}

 	public void setIdTipoTenencia (Long idTipoTenencia){
 		this.idTipoTenencia=idTipoTenencia;
 	}
 	
 	public Long getIdTipoTenencia (){
 		return idTipoTenencia;
 	}
  
  
}