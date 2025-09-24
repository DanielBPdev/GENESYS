package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.TipoTenenciaModeloDTO;
import com.asopagos.afiliados.dto.RespuestaServicioInfraestructuraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/registrarTipoTenencia
 */
public class RegistrarTipoTenencia extends ServiceClient { 
    	private TipoTenenciaModeloDTO tipoTenencia;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaServicioInfraestructuraDTO result;
  
 	public RegistrarTipoTenencia (TipoTenenciaModeloDTO tipoTenencia){
 		super();
		this.tipoTenencia=tipoTenencia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(tipoTenencia == null ? null : Entity.json(tipoTenencia));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (RespuestaServicioInfraestructuraDTO) response.readEntity(RespuestaServicioInfraestructuraDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public RespuestaServicioInfraestructuraDTO getResult() {
		return result;
	}

 
  
  	public void setTipoTenencia (TipoTenenciaModeloDTO tipoTenencia){
 		this.tipoTenencia=tipoTenencia;
 	}
 	
 	public TipoTenenciaModeloDTO getTipoTenencia (){
 		return tipoTenencia;
 	}
  
}