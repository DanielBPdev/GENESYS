package com.asopagos.subsidiomonetario.pagos.clients;

import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/confirmarResultadosAbonosBancarios
 */
public class ConfirmarResultadosAbonosBancarios extends ServiceClient { 
    	private List<CuentaAdministradorSubsidioDTO> listaAbonos;
  
  
 	public ConfirmarResultadosAbonosBancarios (List<CuentaAdministradorSubsidioDTO> listaAbonos){
 		super();
		this.listaAbonos=listaAbonos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(listaAbonos == null ? null : Entity.json(listaAbonos));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaAbonos (List<CuentaAdministradorSubsidioDTO> listaAbonos){
 		this.listaAbonos=listaAbonos;
 	}
 	
 	public List<CuentaAdministradorSubsidioDTO> getListaAbonos (){
 		return listaAbonos;
 	}
  
}