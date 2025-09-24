package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.subsidiomonetario.pagos.dto.CuentaAdministradorSubsidioDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultar/cuentasAdministrador/medioTarjeta/origenAnulacion
 */
public class ConsultarCuentasAdministradorMedioTarjetaOrigenAnulacion extends ServiceClient {
 
  
  	private List<Long> identificadoresCuentas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAdministradorSubsidioDTO> result;
  
 	public ConsultarCuentasAdministradorMedioTarjetaOrigenAnulacion (List<Long> identificadoresCuentas){
 		super();
		this.identificadoresCuentas=identificadoresCuentas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("identificadoresCuentas", identificadoresCuentas.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CuentaAdministradorSubsidioDTO>) response.readEntity(new GenericType<List<CuentaAdministradorSubsidioDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CuentaAdministradorSubsidioDTO> getResult() {
		return result;
	}

 
  	public void setIdentificadoresCuentas (List<Long> identificadoresCuentas){
 		this.identificadoresCuentas=identificadoresCuentas;
 	}
 	
 	public List<Long> getIdentificadoresCuentas (){
 		return identificadoresCuentas;
 	}
  
}