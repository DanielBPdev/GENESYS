package com.asopagos.comunicados.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.util.List;
import com.asopagos.comunicados.dto.RolComunicadoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/destinatarios/buscarComunicadosPorProceso/{proceso}
 */
public class BuscarComunicadosPorProceso extends ServiceClient {
 
  	private ProcesoEnum proceso;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RolComunicadoDTO> result;
  
 	public BuscarComunicadosPorProceso (ProcesoEnum proceso){
 		super();
		this.proceso=proceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("proceso", proceso)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RolComunicadoDTO>) response.readEntity(new GenericType<List<RolComunicadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RolComunicadoDTO> getResult() {
		return result;
	}

 	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  
  
}