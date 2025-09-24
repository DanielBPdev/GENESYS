package com.asopagos.fovis.clients;

import com.asopagos.dto.fovis.SolicitudPostulacionFOVISDTO;
import java.lang.Boolean;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarInformacionHogar
 */
public class ConsultarInformacionHogar extends ServiceClient {
 
  
  	private Boolean aplicaVista360;
  	private String numeroRadicado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudPostulacionFOVISDTO result;
  
 	public ConsultarInformacionHogar (Boolean aplicaVista360,String numeroRadicado){
 		super();
		this.aplicaVista360=aplicaVista360;
		this.numeroRadicado=numeroRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("aplicaVista360", aplicaVista360)
						.queryParam("numeroRadicado", numeroRadicado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudPostulacionFOVISDTO) response.readEntity(SolicitudPostulacionFOVISDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudPostulacionFOVISDTO getResult() {
		return result;
	}

 
  	public void setAplicaVista360 (Boolean aplicaVista360){
 		this.aplicaVista360=aplicaVista360;
 	}
 	
 	public Boolean getAplicaVista360 (){
 		return aplicaVista360;
 	}
  	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
}