package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.InfoPersonaReexpedicionDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/consultarInfoPersonaReexpedicion
 */
public class ConsultarInfoPersonaReexpedicion extends ServiceClient {
 
  
  	private String identficacion;
  	private String tipoIdentificacion;
  	private String numeroTarjeta;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private InfoPersonaReexpedicionDTO result;
  
 	public ConsultarInfoPersonaReexpedicion (String identficacion,String tipoIdentificacion,String numeroTarjeta){
 		super();
		this.identficacion=identficacion;
		this.tipoIdentificacion=tipoIdentificacion;
		this.numeroTarjeta=numeroTarjeta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("identficacion", identficacion)
						.queryParam("tipoIdentificacion", tipoIdentificacion)
						.queryParam("numeroTarjeta", numeroTarjeta)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (InfoPersonaReexpedicionDTO) response.readEntity(InfoPersonaReexpedicionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public InfoPersonaReexpedicionDTO getResult() {
		return result;
	}

 
  	public void setIdentficacion (String identficacion){
 		this.identficacion=identficacion;
 	}
 	
 	public String getIdentficacion (){
 		return identficacion;
 	}
  	public void setTipoIdentificacion (String tipoIdentificacion){
 		this.tipoIdentificacion=tipoIdentificacion;
 	}
 	
 	public String getTipoIdentificacion (){
 		return tipoIdentificacion;
 	}
  	public void setNumeroTarjeta (String numeroTarjeta){
 		this.numeroTarjeta=numeroTarjeta;
 	}
 	
 	public String getNumeroTarjeta (){
 		return numeroTarjeta;
 	}
  
}