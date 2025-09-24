package com.asopagos.empresas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.ProveedorModeloDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empresas/consultarProveedorPorRazonSocial
 */
public class ConsultarProveedorPorRazonSocial extends ServiceClient {
 
  
  	private String razonSocialNombre;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ProveedorModeloDTO> result;
  
 	public ConsultarProveedorPorRazonSocial (String razonSocialNombre){
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
		this.result = (List<ProveedorModeloDTO>) response.readEntity(new GenericType<List<ProveedorModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ProveedorModeloDTO> getResult() {
		return result;
	}

 
  	public void setRazonSocialNombre (String razonSocialNombre){
 		this.razonSocialNombre=razonSocialNombre;
 	}
 	
 	public String getRazonSocialNombre (){
 		return razonSocialNombre;
 	}
  
}