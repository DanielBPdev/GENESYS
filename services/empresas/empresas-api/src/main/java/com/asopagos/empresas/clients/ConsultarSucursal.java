package com.asopagos.empresas.clients;

import com.asopagos.dto.modelo.SucursalEmpresaModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empresas/{idSucursal}/sucursal
 */
public class ConsultarSucursal extends ServiceClient {
 
  	private Long idSucursal;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SucursalEmpresaModeloDTO result;
  
 	public ConsultarSucursal (Long idSucursal){
 		super();
		this.idSucursal=idSucursal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSucursal", idSucursal)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (SucursalEmpresaModeloDTO) response.readEntity(SucursalEmpresaModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public SucursalEmpresaModeloDTO getResult() {
		return result;
	}

 	public void setIdSucursal (Long idSucursal){
 		this.idSucursal=idSucursal;
 	}
 	
 	public Long getIdSucursal (){
 		return idSucursal;
 	}
  
  
}