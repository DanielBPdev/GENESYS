package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.TipoInfraestructuraModeloDTO;
import com.asopagos.afiliados.dto.RespuestaServicioInfraestructuraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/registrarTipoInfraestructura
 */
public class RegistrarTipoInfraestructura extends ServiceClient { 
    	private TipoInfraestructuraModeloDTO tipoInfraestructura;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaServicioInfraestructuraDTO result;
  
 	public RegistrarTipoInfraestructura (TipoInfraestructuraModeloDTO tipoInfraestructura){
 		super();
		this.tipoInfraestructura=tipoInfraestructura;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(tipoInfraestructura == null ? null : Entity.json(tipoInfraestructura));
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

 
  
  	public void setTipoInfraestructura (TipoInfraestructuraModeloDTO tipoInfraestructura){
 		this.tipoInfraestructura=tipoInfraestructura;
 	}
 	
 	public TipoInfraestructuraModeloDTO getTipoInfraestructura (){
 		return tipoInfraestructura;
 	}
  
}