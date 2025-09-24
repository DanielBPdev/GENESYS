// ========================= MASIVO TRANSFERENCIA
package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.GrupoFamiliarDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;

import com.asopagos.services.common.ServiceClient;

public class ConsultarIdsGruposFamiliaresCargueMasivoTransferencia extends ServiceClient{

    private TipoIdentificacionEnum tipoIdentificacion;

	private String numeroIdentificacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public ConsultarIdsGruposFamiliaresCargueMasivoTransferencia (TipoIdentificacionEnum tipoIdentificacion, String numeroIdentificacion){
 		super();
		this.tipoIdentificacion=tipoIdentificacion;
		this.numeroIdentificacion=numeroIdentificacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacion", tipoIdentificacion)
									.queryParam("numeroIdentificacion", numeroIdentificacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<Long> getResult() {
		return result;
	}
    
}
