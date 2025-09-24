package com.asopagos.subsidiomonetario.pagos.composite.clients;

import java.util.List;
import java.lang.Long;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PagosSubsidioMonetarioComposite/generarArchivoBancos219/{esPagoJudicial}
 */
public class GenerarArchivoBancos219 extends ServiceClient { 
  	private Boolean esPagoJudicial;
    	private List<Long> lstIdCuentasBancos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public GenerarArchivoBancos219 (Boolean esPagoJudicial,List<Long> lstIdCuentasBancos){
 		super();
		this.esPagoJudicial=esPagoJudicial;
		this.lstIdCuentasBancos=lstIdCuentasBancos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("esPagoJudicial", esPagoJudicial)
			.request(MediaType.APPLICATION_JSON)
			.post(lstIdCuentasBancos == null ? null : Entity.json(lstIdCuentasBancos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public String getResult() {
		return result;
	}

 	public void setEsPagoJudicial (Boolean esPagoJudicial){
 		this.esPagoJudicial=esPagoJudicial;
 	}
 	
 	public Boolean getEsPagoJudicial (){
 		return esPagoJudicial;
 	}
  
  
  	public void setLstIdCuentasBancos (List<Long> lstIdCuentasBancos){
 		this.lstIdCuentasBancos=lstIdCuentasBancos;
 	}
 	
 	public List<Long> getLstIdCuentasBancos (){
 		return lstIdCuentasBancos;
 	}
  
}