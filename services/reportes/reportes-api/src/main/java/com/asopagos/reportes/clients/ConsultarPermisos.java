package com.asopagos.reportes.clients;

import java.lang.String;
import com.asopagos.dto.DashBoardConsultaDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/dashboard/consultar/permisos/{rolUsuario}
 */
public class ConsultarPermisos extends ServiceClient {
 
  	private String rolUsuario;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DashBoardConsultaDTO result;
  
 	public ConsultarPermisos (String rolUsuario){
 		super();
		this.rolUsuario=rolUsuario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("rolUsuario", rolUsuario)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DashBoardConsultaDTO) response.readEntity(DashBoardConsultaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DashBoardConsultaDTO getResult() {
		return result;
	}

 	public void setRolUsuario (String rolUsuario){
 		this.rolUsuario=rolUsuario;
 	}
 	
 	public String getRolUsuario (){
 		return rolUsuario;
 	}
  
  
}