package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioTotalAbonoDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarCuentaAdminSubsidio/tipoAbono/estadoEnviado/medioDePagoBancos
 */
public class ConsultarCuentaAdminSubsidioTipoAbonoEstadoEnviadoMedioDePagoBancos extends ServiceClient {
 
  
  	private String textoFiltro;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CuentaAdministradorSubsidioTotalAbonoDTO result;
  
 	public ConsultarCuentaAdminSubsidioTipoAbonoEstadoEnviadoMedioDePagoBancos (String textoFiltro){
 		super();
		this.textoFiltro=textoFiltro;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("textoFiltro", textoFiltro)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CuentaAdministradorSubsidioTotalAbonoDTO) response.readEntity(CuentaAdministradorSubsidioTotalAbonoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CuentaAdministradorSubsidioTotalAbonoDTO getResult() {
		return result;
	}

 
  	public void setTextoFiltro (String textoFiltro){
 		this.textoFiltro=textoFiltro;
 	}
 	
 	public String getTextoFiltro (){
 		return textoFiltro;
 	}
  
}