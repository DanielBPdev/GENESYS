package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.subsidiomonetario.pagos.CuentaAdministradorSubsidio;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarCuentasAdministradorSubAbono
 */
public class ConsultarCuentasAdministradorSubAbono extends ServiceClient {
 
  
  	private Long idPersona;
  	private String numeroTarjeta;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<CuentaAdministradorSubsidio> result;
  
 	public ConsultarCuentasAdministradorSubAbono (Long idPersona,String numeroTarjeta){
 		super();
		this.idPersona=idPersona;
		this.numeroTarjeta=numeroTarjeta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPersona", idPersona)
						.queryParam("numeroTarjeta", numeroTarjeta)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<CuentaAdministradorSubsidio>) response.readEntity(new GenericType<List<CuentaAdministradorSubsidio>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<CuentaAdministradorSubsidio> getResult() {
		return result;
	}

 
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  	public void setNumeroTarjeta (String numeroTarjeta){
 		this.numeroTarjeta=numeroTarjeta;
 	}
 	
 	public String getNumeroTarjeta (){
 		return numeroTarjeta;
 	}
  
}