package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.pagos.dto.DetalleTransaccionAsignadoConsultadoDTO;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio PUT
 * /rest/pagosSubsidioMonetario/consultarTransaccionesSubsidio
 */
public class ConsultarTransaccionesSubsidio extends ServiceClient { 
    	private DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAdministradorSubsidioDTO> result;
  
 	public ConsultarTransaccionesSubsidio (DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada){
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
		result = (List<CuentaAdministradorSubsidioDTO>) response.readEntity(new GenericType<List<CuentaAdministradorSubsidioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CuentaAdministradorSubsidioDTO> getResult() {
		return result;
	}

 
  
  	public void setTransaccionConsultada (DetalleTransaccionAsignadoConsultadoDTO transaccionConsultada){
 		this.transaccionConsultada=transaccionConsultada;
 	}
 	
 	public DetalleTransaccionAsignadoConsultadoDTO getTransaccionConsultada (){
 		return transaccionConsultada;
 	}
  
}