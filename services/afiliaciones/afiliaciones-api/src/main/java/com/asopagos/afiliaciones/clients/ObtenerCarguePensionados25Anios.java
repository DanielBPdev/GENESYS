package com.asopagos.afiliaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import com.asopagos.dto.afiliaciones.Afiliado25AniosDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.dto.ConsolaEstadoCargueProcesoDTO;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/externalAPI/afiliacion/consultarClasificacionPensionado
 */
public class ObtenerCarguePensionados25Anios extends ServiceClient {
 
  
  	private Long idCargue;
	private ConsolaEstadoCargueProcesoDTO result;
  
 	public ObtenerCarguePensionados25Anios ( Long idCargue ){
 		super();
		this.idCargue=idCargue;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.queryParam("idCargue", idCargue)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
        this.result = (ConsolaEstadoCargueProcesoDTO) response.readEntity(ConsolaEstadoCargueProcesoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ConsolaEstadoCargueProcesoDTO getResult() {
		return result;
	}
    


}