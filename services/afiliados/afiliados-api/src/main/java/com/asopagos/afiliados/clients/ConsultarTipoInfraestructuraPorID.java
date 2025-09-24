package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.TipoInfraestructuraModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarTipoInfraestructuraPorID/{idTipoInfraestructura}
 */
public class ConsultarTipoInfraestructuraPorID extends ServiceClient {
 
  	private Long idTipoInfraestructura;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private TipoInfraestructuraModeloDTO result;
  
 	public ConsultarTipoInfraestructuraPorID (Long idTipoInfraestructura){
 		super();
		this.idTipoInfraestructura=idTipoInfraestructura;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idTipoInfraestructura", idTipoInfraestructura)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (TipoInfraestructuraModeloDTO) response.readEntity(TipoInfraestructuraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public TipoInfraestructuraModeloDTO getResult() {
		return result;
	}

 	public void setIdTipoInfraestructura (Long idTipoInfraestructura){
 		this.idTipoInfraestructura=idTipoInfraestructura;
 	}
 	
 	public Long getIdTipoInfraestructura (){
 		return idTipoInfraestructura;
 	}
  
  
}