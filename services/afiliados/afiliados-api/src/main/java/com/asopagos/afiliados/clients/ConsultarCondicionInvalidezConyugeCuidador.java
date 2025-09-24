package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.entidades.ccf.personas.CondicionInvalidez;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;



import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarCondicionInvalidezVigentePersona
 */
public class ConsultarCondicionInvalidezConyugeCuidador extends ServiceClient {
 
  
  	private TipoIdentificacionEnum tipoIdentificacionConyuge;

	private String numeroIdentificacionConyuge;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CondicionInvalidez> result;
  
 	public ConsultarCondicionInvalidezConyugeCuidador (TipoIdentificacionEnum tipoIdentificacionConyuge, String numeroIdentificacionConyuge){
 		super();
		this.tipoIdentificacionConyuge=tipoIdentificacionConyuge;
		this.numeroIdentificacionConyuge=numeroIdentificacionConyuge;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoIdentificacionConyuge", tipoIdentificacionConyuge)
									.queryParam("numeroIdentificacionConyuge", numeroIdentificacionConyuge)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CondicionInvalidez>) response.readEntity(new GenericType<List<CondicionInvalidez>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CondicionInvalidez> getResult() {
		return result;
	}

 
  
}