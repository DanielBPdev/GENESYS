package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.InfraestructuraModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarInfraestructuraPorID/{idInfraestructura}
 */
public class ConsultarInfraestructuraPorID extends ServiceClient {
 
  	private Long idInfraestructura;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InfraestructuraModeloDTO result;
  
 	public ConsultarInfraestructuraPorID (Long idInfraestructura){
 		super();
		this.idInfraestructura=idInfraestructura;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idInfraestructura", idInfraestructura)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InfraestructuraModeloDTO) response.readEntity(InfraestructuraModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InfraestructuraModeloDTO getResult() {
		return result;
	}

 	public void setIdInfraestructura (Long idInfraestructura){
 		this.idInfraestructura=idInfraestructura;
 	}
 	
 	public Long getIdInfraestructura (){
 		return idInfraestructura;
 	}
  
  
}