package com.asopagos.subsidiomonetario.pagos.clients;

import java.lang.Double;
import com.asopagos.enumeraciones.personas.TipoMedioDePagoEnum;
import com.asopagos.enumeraciones.personas.TipoIdentificacionEnum;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarSaldoSubsidioSinOperacion
 */
public class ConsultarSaldoSubsidioSinOperacion extends ServiceClient {
 
  
  	private String numeroIdAdmin;
  	private TipoMedioDePagoEnum medioDePago;
  	private TipoIdentificacionEnum tipoIdAdmin;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Double result;
  
 	public ConsultarSaldoSubsidioSinOperacion (String numeroIdAdmin,TipoMedioDePagoEnum medioDePago,TipoIdentificacionEnum tipoIdAdmin){
 		super();
		this.numeroIdAdmin=numeroIdAdmin;
		this.medioDePago=medioDePago;
		this.tipoIdAdmin=tipoIdAdmin;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("numeroIdAdmin", numeroIdAdmin)
						.queryParam("medioDePago", medioDePago)
						.queryParam("tipoIdAdmin", tipoIdAdmin)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Double) response.readEntity(Double.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Double getResult() {
		return result;
	}

 
  	public void setNumeroIdAdmin (String numeroIdAdmin){
 		this.numeroIdAdmin=numeroIdAdmin;
 	}
 	
 	public String getNumeroIdAdmin (){
 		return numeroIdAdmin;
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