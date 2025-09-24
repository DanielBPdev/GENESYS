package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarMediosDePagos/AdminSubsidio/{idAdminSubsidio}
 */
public class ConsultarMediosDePagosRelacionadosAdminSubsidio extends ServiceClient {
 
  	private Long idAdminSubsidio;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TipoMedioDePagoEnum> result;
  
 	public ConsultarMediosDePagosRelacionadosAdminSubsidio (Long idAdminSubsidio){
 		super();
		this.idAdminSubsidio=idAdminSubsidio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idAdminSubsidio", idAdminSubsidio)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<TipoMedioDePagoEnum>) response.readEntity(new GenericType<List<TipoMedioDePagoEnum>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<TipoMedioDePagoEnum> getResult() {
		return result;
	}

 	public void setIdAdminSubsidio (Long idAdminSubsidio){
 		this.idAdminSubsidio=idAdminSubsidio;
 	}
 	
 	public Long getIdAdminSubsidio (){
 		return idAdminSubsidio;
 	}
  
  
}