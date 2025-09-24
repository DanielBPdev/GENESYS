package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.TipoInfraestructuraModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarTipoInfraestructuraPorIdSSF/{idSsfTipoInfraestructura}
 */
public class ConsultarTipoInfraestructuraPorIdSSF extends ServiceClient {
 
  	private String idSsfTipoInfraestructura;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private TipoInfraestructuraModeloDTO result;
  
 	public ConsultarTipoInfraestructuraPorIdSSF (String idSsfTipoInfraestructura){
 		super();
		this.idSsfTipoInfraestructura=idSsfTipoInfraestructura;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSsfTipoInfraestructura", idSsfTipoInfraestructura)
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

 	public void setIdSsfTipoInfraestructura (String idSsfTipoInfraestructura){
 		this.idSsfTipoInfraestructura=idSsfTipoInfraestructura;
 	}
 	
 	public String getIdSsfTipoInfraestructura (){
 		return idSsfTipoInfraestructura;
 	}
  
  
}