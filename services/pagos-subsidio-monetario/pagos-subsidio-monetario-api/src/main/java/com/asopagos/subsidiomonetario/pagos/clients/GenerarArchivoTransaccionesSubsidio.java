package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO;
import java.util.Map;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/generarArchivoTransaccionesSubsidio
 */
public class GenerarArchivoTransaccionesSubsidio extends ServiceClient { 
    	private DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public GenerarArchivoTransaccionesSubsidio (DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada){
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
		result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<String,String> getResult() {
		return result;
	}

 
  
  	public void setTransaccionConsultada (DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada){
 		this.transaccionConsultada=transaccionConsultada;
 	}
 	
 	public DetalleTransaccionAsignadoConsultadoDTO getTransaccionConsultada (){
 		return transaccionConsultada;
 	}
  
}