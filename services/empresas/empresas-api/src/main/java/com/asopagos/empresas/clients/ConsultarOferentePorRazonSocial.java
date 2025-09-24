package com.asopagos.empresas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.OferenteModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empresas/consultarOferentePorRazonSocial
 */
public class ConsultarOferentePorRazonSocial extends ServiceClient {
 
  
  	private String razonSocialNombre;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<OferenteModeloDTO> result;
  
 	public ConsultarOferentePorRazonSocial (String razonSocialNombre){
 		super();
		this.razonSocialNombre=razonSocialNombre;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("razonSocialNombre", razonSocialNombre)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<OferenteModeloDTO>) response.readEntity(new GenericType<List<OferenteModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<OferenteModeloDTO> getResult() {
		return result;
	}

 
  	public void setRazonSocialNombre (String razonSocialNombre){
 		this.razonSocialNombre=razonSocialNombre;
 	}
 	
 	public String getRazonSocialNombre (){
 		return razonSocialNombre;
 	}
  
}