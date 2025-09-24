package com.asopagos.aportes.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.AporteDetalladoModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarAporteDetallado/{idAporteDetallado}
 */
public class ConsultarAporteDetallado extends ServiceClient {
 
  	private Long idAporteDetallado;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private AporteDetalladoModeloDTO result;
  
 	public ConsultarAporteDetallado (Long idAporteDetallado){
 		super();
		this.idAporteDetallado=idAporteDetallado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idAporteDetallado", idAporteDetallado)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (AporteDetalladoModeloDTO) response.readEntity(AporteDetalladoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public AporteDetalladoModeloDTO getResult() {
		return result;
	}

 	public void setIdAporteDetallado (Long idAporteDetallado){
 		this.idAporteDetallado=idAporteDetallado;
 	}
 	
 	public Long getIdAporteDetallado (){
 		return idAporteDetallado;
 	}
  
  
}