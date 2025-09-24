
package com.asopagos.afiliaciones.clients;

import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.afiliaciones.dto.ConsultaAfiliadoRecaudosPagosDTO;
import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/consultarUltimaClasificacionPersona
 */
public class ConsultaAfiliadoRecaudosPagos extends ServiceClient {
 
    private String idTransaccion;

  	private String numeroIdentificacion;

    private String tipoIdentificacion;


	private String additionalData;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
      private ConsultaAfiliadoRecaudosPagosDTO result;
  
 	public ConsultaAfiliadoRecaudosPagos (String idTransaccion,String tipoIdentificacion,String numeroIdentificacion,String additionalData){
 		super();
		this.idTransaccion=idTransaccion;
        this.tipoIdentificacion=tipoIdentificacion;
		this.numeroIdentificacion=numeroIdentificacion;
        this.additionalData=additionalData;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
        .queryParam("TransactionId", idTransaccion)
        .queryParam("IdentificacionType ", tipoIdentificacion)
		.queryParam("Identification", numeroIdentificacion)
        .queryParam("AdditionalData", additionalData )
		.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
    @Override
	protected void getResultData(Response response) {
		result = (ConsultaAfiliadoRecaudosPagosDTO) response.readEntity(ConsultaAfiliadoRecaudosPagosDTO.class);
	}


	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ConsultaAfiliadoRecaudosPagosDTO getResult() {
		return result;
	}

 

  
}