package com.asopagos.subsidiomonetario.clients;

import com.asopagos.subsidiomonetario.dto.ArchivoLiquidacionSubsidioModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/archivosLiquidacion/consultarPorId/{idArchivoLiquidacion}
 */
public class ConsultarArchivosLiquidacionPorId extends ServiceClient {
 
  	private Long idArchivoLiquidacion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ArchivoLiquidacionSubsidioModeloDTO result;
  
 	public ConsultarArchivosLiquidacionPorId(Long idArchivoLiquidacion){
 		super();
		this.idArchivoLiquidacion=idArchivoLiquidacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idArchivoLiquidacion", idArchivoLiquidacion)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ArchivoLiquidacionSubsidioModeloDTO) response.readEntity(ArchivoLiquidacionSubsidioModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ArchivoLiquidacionSubsidioModeloDTO getResult() {
		return result;
	}

 	
  
  
}