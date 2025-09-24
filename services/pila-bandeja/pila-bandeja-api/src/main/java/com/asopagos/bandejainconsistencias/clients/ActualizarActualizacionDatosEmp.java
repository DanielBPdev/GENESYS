package com.asopagos.bandejainconsistencias.clients;

import java.util.List;
import com.asopagos.dto.aportes.ActualizacionDatosEmpleadorModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pilaBandeja/actualizarDatosEmpleador
 */
public class ActualizarActualizacionDatosEmp extends ServiceClient { 
    	private List<ActualizacionDatosEmpleadorModeloDTO> listaActualizacion;
  
  
 	public ActualizarActualizacionDatosEmp (List<ActualizacionDatosEmpleadorModeloDTO> listaActualizacion){
 		super();
		this.listaActualizacion=listaActualizacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaActualizacion == null ? null : Entity.json(listaActualizacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaActualizacion (List<ActualizacionDatosEmpleadorModeloDTO> listaActualizacion){
 		this.listaActualizacion=listaActualizacion;
 	}
 	
 	public List<ActualizacionDatosEmpleadorModeloDTO> getListaActualizacion (){
 		return listaActualizacion;
 	}
  
}