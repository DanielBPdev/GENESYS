package com.asopagos.cartera.clients;

import com.asopagos.dto.cartera.GestionCarteraMoraDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/cartera/consultarTotalDetalleAportantesPanel
 */
public class ConsultarTotalDetalleAportantesPanel extends ServiceClient {
 
  
  	private String usuarioAnalista;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private GestionCarteraMoraDTO result;
  
 	public ConsultarTotalDetalleAportantesPanel (String usuarioAnalista){
 		super();
		this.usuarioAnalista=usuarioAnalista;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("usuarioAnalista", usuarioAnalista)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (GestionCarteraMoraDTO) response.readEntity(GestionCarteraMoraDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public GestionCarteraMoraDTO getResult() {
		return result;
	}

 
  	public void setUsuarioAnalista (String usuarioAnalista){
 		this.usuarioAnalista=usuarioAnalista;
 	}
 	
 	public String getUsuarioAnalista (){
 		return usuarioAnalista;
 	}
  
}