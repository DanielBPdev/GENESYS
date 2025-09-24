package com.asopagos.novedades.clients;

import java.lang.Long;
import com.asopagos.dto.DiferenciasCargueActualizacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedadesCargueMultiple/consultarDiferenciaCargueArchivoActualizacion
 */
public class ConsultarDiferenciaCargueArchivoActualizacion extends ServiceClient {
 
  
  	private Long idDiferencia;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DiferenciasCargueActualizacionDTO result;
  
 	public ConsultarDiferenciaCargueArchivoActualizacion (Long idDiferencia){
 		super();
		this.idDiferencia=idDiferencia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idDiferencia", idDiferencia)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DiferenciasCargueActualizacionDTO) response.readEntity(DiferenciasCargueActualizacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DiferenciasCargueActualizacionDTO getResult() {
		return result;
	}

 
  	public void setIdDiferencia (Long idDiferencia){
 		this.idDiferencia=idDiferencia;
 	}
 	
 	public Long getIdDiferencia (){
 		return idDiferencia;
 	}
  
}