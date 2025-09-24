package com.asopagos.pila.composite.clients;

import com.asopagos.aportes.dto.CuentaAporteDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/PilaComposite/consultarCuentaAportePlanillaOriginal
 */
public class ConsultarCuentaAportePlanillaOriginal extends ServiceClient {
 
  
  	private Long idPlanillaOriginal;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAporteDTO> result;
  
 	public ConsultarCuentaAportePlanillaOriginal (Long idPlanillaOriginal){
 		super();
		this.idPlanillaOriginal=idPlanillaOriginal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPlanillaOriginal", idPlanillaOriginal)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CuentaAporteDTO>) response.readEntity(new GenericType<List<CuentaAporteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CuentaAporteDTO> getResult() {
		return result;
	}

 
  	public void setIdPlanillaOriginal (Long idPlanillaOriginal){
 		this.idPlanillaOriginal=idPlanillaOriginal;
 	}
 	
 	public Long getIdPlanillaOriginal (){
 		return idPlanillaOriginal;
 	}
  
}