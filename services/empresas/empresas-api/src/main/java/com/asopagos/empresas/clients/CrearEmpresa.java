package com.asopagos.empresas.clients;

import com.asopagos.dto.modelo.EmpresaModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empresas/crearEmpresa
 */
public class CrearEmpresa extends ServiceClient { 
    	private EmpresaModeloDTO empresaModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Long result;
  
 	public CrearEmpresa (EmpresaModeloDTO empresaModeloDTO){
 		super();
		this.empresaModeloDTO=empresaModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(empresaModeloDTO == null ? null : Entity.json(empresaModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Long) response.readEntity(Long.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Long getResult() {
		return result;
	}

 
  
  	public void setEmpresaModeloDTO (EmpresaModeloDTO empresaModeloDTO){
 		this.empresaModeloDTO=empresaModeloDTO;
 	}
 	
 	public EmpresaModeloDTO getEmpresaModeloDTO (){
 		return empresaModeloDTO;
 	}
  
}