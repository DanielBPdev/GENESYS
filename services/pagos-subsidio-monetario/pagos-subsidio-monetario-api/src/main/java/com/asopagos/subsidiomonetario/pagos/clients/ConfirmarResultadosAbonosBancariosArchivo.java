package com.asopagos.subsidiomonetario.pagos.clients;

import java.util.List;
import com.asopagos.dto.modelo.ConfirmacionAbonoBancarioCargueDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/confirmarResultadosAbonosBancariosArchivo
 */
public class ConfirmarResultadosAbonosBancariosArchivo extends ServiceClient { 
  	private String userEmail;
    	private List<ConfirmacionAbonoBancarioCargueDTO> listaAbonos;
  
  
 	public ConfirmarResultadosAbonosBancariosArchivo (String userEmail,List<ConfirmacionAbonoBancarioCargueDTO> listaAbonos){
 		super();
		this.userEmail=userEmail;
		this.listaAbonos=listaAbonos;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("userEmail", userEmail)
			.request(MediaType.APPLICATION_JSON)
			.put(listaAbonos == null ? null : Entity.json(listaAbonos));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 	public void setUserEmail (String userEmail){
 		this.userEmail=userEmail;
 	}
 	
 	public String getUserEmail (){
 		return userEmail;
 	}
  
  
  	public void setListaAbonos (List<ConfirmacionAbonoBancarioCargueDTO> listaAbonos){
 		this.listaAbonos=listaAbonos;
 	}
 	
 	public List<ConfirmacionAbonoBancarioCargueDTO> getListaAbonos (){
 		return listaAbonos;
 	}
  
}