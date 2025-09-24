package com.asopagos.cartera.clients;

import com.asopagos.dto.modelo.SolicitudGestionCobroElectronicoModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/{numeroRadicado}/consultarSolicitudGestionCobroElectronico
 */
public class ConsultarSolicitudGestionCobroElectronico extends ServiceClient {
 
  	private String numeroRadicado;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudGestionCobroElectronicoModeloDTO result;
  
 	public ConsultarSolicitudGestionCobroElectronico (String numeroRadicado){
 		super();
		this.numeroRadicado=numeroRadicado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicado", numeroRadicado)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SolicitudGestionCobroElectronicoModeloDTO) response.readEntity(SolicitudGestionCobroElectronicoModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SolicitudGestionCobroElectronicoModeloDTO getResult() {
		return result;
	}

 	public void setNumeroRadicado (String numeroRadicado){
 		this.numeroRadicado=numeroRadicado;
 	}
 	
 	public String getNumeroRadicado (){
 		return numeroRadicado;
 	}
  
  
}