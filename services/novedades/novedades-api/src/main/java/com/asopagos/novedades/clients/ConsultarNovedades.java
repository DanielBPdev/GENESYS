package com.asopagos.novedades.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.util.List;
import com.asopagos.dto.modelo.ParametrizacionNovedadModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/{procesoEnum}/consultarNovedades
 */
public class ConsultarNovedades extends ServiceClient {
 
  	private ProcesoEnum procesoEnum;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ParametrizacionNovedadModeloDTO> result;
  
 	public ConsultarNovedades (ProcesoEnum procesoEnum){
 		super();
		this.procesoEnum=procesoEnum;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("procesoEnum", procesoEnum)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ParametrizacionNovedadModeloDTO>) response.readEntity(new GenericType<List<ParametrizacionNovedadModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ParametrizacionNovedadModeloDTO> getResult() {
		return result;
	}

 	public void setProcesoEnum (ProcesoEnum procesoEnum){
 		this.procesoEnum=procesoEnum;
 	}
 	
 	public ProcesoEnum getProcesoEnum (){
 		return procesoEnum;
 	}
  
  
}