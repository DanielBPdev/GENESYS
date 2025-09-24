package com.asopagos.aportes.clients;

import java.util.List;
import com.asopagos.aportes.dto.ConsultaPresenciaNovedadesDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/crearTemAporteProcesadosNuevos
 */
public class CrearTemAporteProcesadosNuevos extends ServiceClient { 
    	private List<ConsultaPresenciaNovedadesDTO> listaPresneciaNovedades;
  
  
 	public CrearTemAporteProcesadosNuevos (List<ConsultaPresenciaNovedadesDTO> listaPresneciaNovedades){
 		super();
		this.listaPresneciaNovedades=listaPresneciaNovedades;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listaPresneciaNovedades == null ? null : Entity.json(listaPresneciaNovedades));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
	}
	

 
  
  	public void setListaPresneciaNovedades (List<ConsultaPresenciaNovedadesDTO> listaPresneciaNovedades){
 		this.listaPresneciaNovedades=listaPresneciaNovedades;
 	}
 	
 	public List<ConsultaPresenciaNovedadesDTO> getListaPresneciaNovedades (){
 		return listaPresneciaNovedades;
 	}
  
}