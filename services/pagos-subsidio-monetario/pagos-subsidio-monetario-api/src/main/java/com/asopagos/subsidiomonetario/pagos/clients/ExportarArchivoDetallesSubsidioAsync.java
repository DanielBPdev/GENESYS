package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/exportarArchivoDetallesSubsidioAsync
 */
public class ExportarArchivoDetallesSubsidioAsync extends ServiceClient { 
    	private DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada;
  
  
 	public ExportarArchivoDetallesSubsidioAsync (DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada){
 		super();
		this.transaccionConsultada=transaccionConsultada;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.put(transaccionConsultada == null ? null : Entity.json(transaccionConsultada));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setTransaccionConsultada (DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada){
 		this.transaccionConsultada=transaccionConsultada;
 	}
 	
 	public DetalleTransaccionAsignadoConsultadoDTO getTransaccionConsultada (){
 		return transaccionConsultada;
 	}
  
}