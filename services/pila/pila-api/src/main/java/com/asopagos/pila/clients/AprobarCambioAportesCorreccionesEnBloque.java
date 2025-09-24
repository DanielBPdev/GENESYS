package com.asopagos.pila.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.pila.dto.RegistrarCorreccionAdicionDTO;
import com.asopagos.pila.dto.ResultadoAprobacionCorreccionAporteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pila/aprobarCambioAportesCorreccionesEnBloque
 */
public class AprobarCambioAportesCorreccionesEnBloque extends ServiceClient { 
    	private List<RegistrarCorreccionAdicionDTO> listadoCriteriosSimulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ResultadoAprobacionCorreccionAporteDTO> result;
  
 	public AprobarCambioAportesCorreccionesEnBloque (List<RegistrarCorreccionAdicionDTO> listadoCriteriosSimulacion){
 		super();
		this.listadoCriteriosSimulacion=listadoCriteriosSimulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(listadoCriteriosSimulacion == null ? null : Entity.json(listadoCriteriosSimulacion));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<ResultadoAprobacionCorreccionAporteDTO>) response.readEntity(new GenericType<List<ResultadoAprobacionCorreccionAporteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<ResultadoAprobacionCorreccionAporteDTO> getResult() {
		return result;
	}

 
  
  	public void setListadoCriteriosSimulacion (List<RegistrarCorreccionAdicionDTO> listadoCriteriosSimulacion){
 		this.listadoCriteriosSimulacion=listadoCriteriosSimulacion;
 	}
 	
 	public List<RegistrarCorreccionAdicionDTO> getListadoCriteriosSimulacion (){
 		return listadoCriteriosSimulacion;
 	}
  
}