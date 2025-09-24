package com.asopagos.novedades.clients;

import com.asopagos.novedades.dto.DatosNovedadEmpleadorPaginadoDTO;
import com.asopagos.novedades.dto.FiltrosDatosNovedadDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedades/consultarNovedadesEmpleador
 */
public class ConsultarNovedadesEmpleador extends ServiceClient { 
    	private FiltrosDatosNovedadDTO filtrosDatosNovedad;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatosNovedadEmpleadorPaginadoDTO result;
  
 	public ConsultarNovedadesEmpleador (FiltrosDatosNovedadDTO filtrosDatosNovedad){
 		super();
		this.filtrosDatosNovedad=filtrosDatosNovedad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(filtrosDatosNovedad == null ? null : Entity.json(filtrosDatosNovedad));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (DatosNovedadEmpleadorPaginadoDTO) response.readEntity(DatosNovedadEmpleadorPaginadoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DatosNovedadEmpleadorPaginadoDTO getResult() {
		return result;
	}

 
  
  	public void setFiltrosDatosNovedad (FiltrosDatosNovedadDTO filtrosDatosNovedad){
 		this.filtrosDatosNovedad=filtrosDatosNovedad;
 	}
 	
 	public FiltrosDatosNovedadDTO getFiltrosDatosNovedad (){
 		return filtrosDatosNovedad;
 	}
  
}