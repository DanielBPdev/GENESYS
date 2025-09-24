package com.asopagos.afiliados.clients;

import com.asopagos.novedades.dto.DatosEmpleadorNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/cambiarMarcaEmpresaTrasladadaCCF
 */
public class CambiarMarcaEmpresaTrasladadaCCF extends ServiceClient { 
    	private DatosEmpleadorNovedadDTO datosEmpleador;
  
  
 	public CambiarMarcaEmpresaTrasladadaCCF (DatosEmpleadorNovedadDTO datosEmpleador){
 		super();
		this.datosEmpleador=datosEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosEmpleador == null ? null : Entity.json(datosEmpleador));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setDatosEmpleador (DatosEmpleadorNovedadDTO datosEmpleador){
 		this.datosEmpleador=datosEmpleador;
 	}
 	
 	public DatosEmpleadorNovedadDTO getDatosEmpleador (){
 		return datosEmpleador;
 	}
  
}