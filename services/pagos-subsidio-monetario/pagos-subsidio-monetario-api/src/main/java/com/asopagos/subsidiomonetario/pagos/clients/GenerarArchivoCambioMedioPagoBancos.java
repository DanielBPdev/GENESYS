package com.asopagos.subsidiomonetario.pagos.clients;

import java.util.List;
import java.lang.Long;
import com.asopagos.dto.InformacionArchivoDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/generarArchivo/cambioMedioPago/bancos
 */
public class GenerarArchivoCambioMedioPagoBancos extends ServiceClient { 
   	private Boolean esPagoJudicial;
   	private List<Long> lstIdCuentasBancos;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InformacionArchivoDTO result;
  
 	public GenerarArchivoCambioMedioPagoBancos (Boolean esPagoJudicial,List<Long> lstIdCuentasBancos){
 		super();
		this.esPagoJudicial=esPagoJudicial;
		this.lstIdCuentasBancos=lstIdCuentasBancos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("esPagoJudicial", esPagoJudicial)
			.request(MediaType.APPLICATION_JSON)
			.post(lstIdCuentasBancos == null ? null : Entity.json(lstIdCuentasBancos));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (InformacionArchivoDTO) response.readEntity(InformacionArchivoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public InformacionArchivoDTO getResult() {
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