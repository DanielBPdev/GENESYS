package com.asopagos.empresas.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.entidades.ccf.core.UbicacionEmpresa;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empresas/{idEmpresa}/ubicaciones
 */
public class ConsultarUbicacionesEmpresa extends ServiceClient {
 
  	private Long idEmpresa;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<UbicacionEmpresa> result;
  
 	public ConsultarUbicacionesEmpresa (Long idEmpresa){
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
		this.result = (List<UbicacionEmpresa>) response.readEntity(new GenericType<List<UbicacionEmpresa>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<UbicacionEmpresa> getResult() {
		return result;
	}

 	public void setIdEmpresa (Long idEmpresa){
 		this.idEmpresa=idEmpresa;
 	}
 	
 	public Long getIdEmpresa (){
 		return idEmpresa;
 	}
  
  
}