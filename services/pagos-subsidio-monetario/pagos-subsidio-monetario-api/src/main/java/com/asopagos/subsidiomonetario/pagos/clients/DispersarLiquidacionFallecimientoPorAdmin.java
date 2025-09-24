package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/dispersarLiquidacionFallecimiento/porAdmin
 */
public class DispersarLiquidacionFallecimientoPorAdmin extends ServiceClient { 
    	private List<Long> lstIdsCondicionesBeneficiarios;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<Long> result;
  
 	public DispersarLiquidacionFallecimientoPorAdmin (List<Long> lstIdsCondicionesBeneficiarios){
 		super();
		this.lstIdsCondicionesBeneficiarios=lstIdsCondicionesBeneficiarios;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(lstIdsCondicionesBeneficiarios == null ? null : Entity.json(lstIdsCondicionesBeneficiarios));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<Long>) response.readEntity(new GenericType<List<Long>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<Long> getResult() {
		return result;
	}

 
  
  	public void setLstIdsCondicionesBeneficiarios (List<Long> lstIdsCondicionesBeneficiarios){
 		this.lstIdsCondicionesBeneficiarios=lstIdsCondicionesBeneficiarios;
 	}
 	
 	public List<Long> getLstIdsCondicionesBeneficiarios (){
 		return lstIdsCondicionesBeneficiarios;
 	}
  
}