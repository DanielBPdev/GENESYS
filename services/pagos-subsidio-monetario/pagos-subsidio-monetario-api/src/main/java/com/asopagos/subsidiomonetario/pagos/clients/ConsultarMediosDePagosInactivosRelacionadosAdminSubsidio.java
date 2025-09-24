package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/consultarMediosDePagosInactivos/AdminSubsidio/{idAdminSubsidio}
 */
public class ConsultarMediosDePagosInactivosRelacionadosAdminSubsidio extends ServiceClient { 
  	private Long idAdminSubsidio;
    	private List<Long> cuentas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TipoMedioDePagoEnum> result;
  
 	public ConsultarMediosDePagosInactivosRelacionadosAdminSubsidio (Long idAdminSubsidio,List<Long> cuentas){
 		super();
		this.idAdminSubsidio=idAdminSubsidio;
		this.cuentas=cuentas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idAdminSubsidio", idAdminSubsidio)
			.request(MediaType.APPLICATION_JSON)
			.post(cuentas == null ? null : Entity.json(cuentas));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<TipoMedioDePagoEnum>) response.readEntity(new GenericType<List<TipoMedioDePagoEnum>>(){});
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
  
  
  	public void setCuentas (List<Long> cuentas){
 		this.cuentas=cuentas;
 	}
 	
 	public List<Long> getCuentas (){
 		return cuentas;
 	}
  
}