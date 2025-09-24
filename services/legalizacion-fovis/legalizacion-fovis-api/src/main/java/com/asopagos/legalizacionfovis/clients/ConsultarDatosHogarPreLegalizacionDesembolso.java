package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarDatosHogarPreLegalizacionDesembolso
 */
public class ConsultarDatosHogarPreLegalizacionDesembolso extends ServiceClient {
 
  
  	private String numeroRadicado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudPostulacionFOVISDTO result;
  
 	public ConsultarDatosHogarPreLegalizacionDesembolso (String numeroRadicado){
 		super();
		this.numeroRadicado=numeroRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroRadicado", numeroRadicado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudPostulacionFOVISDTO) response.readEntity(SolicitudPostulacionFOVISDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudPostulacionFOVISDTO getResult() {
		return result;
	}

 
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
}