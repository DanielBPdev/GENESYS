package com.asopagos.empresas.clients;

import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empresas/{idEmpresa}/obtenerCodigoDisponibleSucursal
 */
public class ObtenerCodigoDisponibleSucursal extends ServiceClient {
 
  	private Long idEmpresa;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private String result;
  
 	public ObtenerCodigoDisponibleSucursal (Long idEmpresa){
 		super();
		this.idEmpresa=idEmpresa;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEmpresa", idEmpresa)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (String) response.readEntity(String.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public String getResult() {
		return result;
	}

 	public void setIdEmpresa (Long idEmpresa){
 		this.idEmpresa=idEmpresa;
 	}
 	
 	public Long getIdEmpresa (){
 		return idEmpresa;
 	}
  
  
}