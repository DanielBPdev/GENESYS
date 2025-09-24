package com.asopagos.afiliados.clients;

import com.asopagos.dto.modelo.InfraestructuraModeloDTO;
import com.asopagos.afiliados.dto.RespuestaServicioInfraestructuraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/registrarInfraestructura
 */
public class RegistrarInfraestructura extends ServiceClient { 
    	private InfraestructuraModeloDTO infraestructura;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RespuestaServicioInfraestructuraDTO result;
  
 	public RegistrarInfraestructura (InfraestructuraModeloDTO infraestructura){
 		super();
		this.infraestructura=infraestructura;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(infraestructura == null ? null : Entity.json(infraestructura));
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

 
  
  	public void setInfraestructura (InfraestructuraModeloDTO infraestructura){
 		this.infraestructura=infraestructura;
 	}
 	
 	public InfraestructuraModeloDTO getInfraestructura (){
 		return infraestructura;
 	}
  
}