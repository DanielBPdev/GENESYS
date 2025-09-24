package com.asopagos.empresas.clients;

import com.asopagos.dto.modelo.EmpresaModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empresas/consultarEmpresaPorId
 */
public class ConsultarEmpresaPorId extends ServiceClient {
 
  
  	private Long idEmpresa;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EmpresaModeloDTO result;
  
 	public ConsultarEmpresaPorId (Long idEmpresa){
 		super();
		this.idEmpresa=idEmpresa;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idEmpresa", idEmpresa)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (EmpresaModeloDTO) response.readEntity(EmpresaModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public EmpresaModeloDTO getResult() {
		return result;
	}

 
  	public void setIdEmpresa (Long idEmpresa){
 		this.idEmpresa=idEmpresa;
 	}
 	
 	public Long getIdEmpresa (){
 		return idEmpresa;
 	}
  
}