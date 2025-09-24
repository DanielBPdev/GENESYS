package com.asopagos.constantes.parametros.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Short;
import com.asopagos.constantes.parametros.dto.AreaCajaCompensacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/constantesparametros/dependenciasCaja
 */
public class ConsultarDependenciasCCF extends ServiceClient {
 
  
  	private Short idDependencia;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AreaCajaCompensacionDTO> result;
  
 	public ConsultarDependenciasCCF (Short idDependencia){
 		super();
		this.idDependencia=idDependencia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idDependencia", idDependencia)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<AreaCajaCompensacionDTO>) response.readEntity(new GenericType<List<AreaCajaCompensacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<AreaCajaCompensacionDTO> getResult() {
		return result;
	}

 
  	public void setIdDependencia (Short idDependencia){
 		this.idDependencia=idDependencia;
 	}
 	
 	public Short getIdDependencia (){
 		return idDependencia;
 	}
  
}