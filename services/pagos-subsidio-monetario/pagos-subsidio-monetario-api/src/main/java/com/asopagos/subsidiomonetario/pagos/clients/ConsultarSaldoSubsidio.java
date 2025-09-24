package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import java.util.Map;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarSaldoSubsidio
 */
public class ConsultarSaldoSubsidio extends ServiceClient {
 
  
  	private String numeroIdAdmin;
  	private String user;
  	private TipoMedioDePagoEnum medioDePago;
  	private TipoIdentificacionEnum tipoIdAdmin;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Map<String,String> result;
  
 	public ConsultarSaldoSubsidio (String numeroIdAdmin,String user,TipoMedioDePagoEnum medioDePago,TipoIdentificacionEnum tipoIdAdmin){
 		super();
		this.numeroIdAdmin=numeroIdAdmin;
		this.user=user;
		this.medioDePago=medioDePago;
		this.tipoIdAdmin=tipoIdAdmin;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdAdmin", numeroIdAdmin)
						.queryParam("user", user)
						.queryParam("medioDePago", medioDePago)
						.queryParam("tipoIdAdmin", tipoIdAdmin)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Map<String,String>) response.readEntity(Map.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Map<String,String> getResult() {
		return result;
	}

 
  	public void setNumeroIdAdmin (String numeroIdAdmin){
 		this.numeroIdAdmin=numeroIdAdmin;
 	}
 	
 	public String getNumeroIdAdmin (){
 		return numeroIdAdmin;
 	}
  	public void setUser (String user){
 		this.user=user;
 	}
 	
 	public String getUser (){
 		return user;
 	}
  	public void setMedioDePago (TipoMedioDePagoEnum medioDePago){
 		this.medioDePago=medioDePago;
 	}
 	
 	public TipoMedioDePagoEnum getMedioDePago (){
 		return medioDePago;
 	}
  	public void setTipoIdAdmin (TipoIdentificacionEnum tipoIdAdmin){
 		this.tipoIdAdmin=tipoIdAdmin;
 	}
 	
 	public TipoIdentificacionEnum getTipoIdAdmin (){
 		return tipoIdAdmin;
 	}
  
}