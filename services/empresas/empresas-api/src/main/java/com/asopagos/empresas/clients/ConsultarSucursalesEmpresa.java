package com.asopagos.empresas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.ccf.core.SucursalEmpresa;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empresas/{idEmpresa}/sucursales
 */
public class ConsultarSucursalesEmpresa extends ServiceClient {
 
  	private Long idEmpresa;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SucursalEmpresa> result;
  
 	public ConsultarSucursalesEmpresa (Long idEmpresa){
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
		this.result = (List<SucursalEmpresa>) response.readEntity(new GenericType<List<SucursalEmpresa>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<SucursalEmpresa> getResult() {
		return result;
	}

 	public void setIdEmpresa (Long idEmpresa){
 		this.idEmpresa=idEmpresa;
 	}
 	
 	public Long getIdEmpresa (){
 		return idEmpresa;
 	}
  
  
}