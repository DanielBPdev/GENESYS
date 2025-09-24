package com.asopagos.comunicados.clients;

import com.asopagos.entidades.ccf.comunicados.Comunicado;
import com.asopagos.notificaciones.dto.ComunicadoPersistenciaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/comunicados/construirPersistirComunicado
 */
public class ConstruirPersistirComunicado extends ServiceClient { 
    	private ComunicadoPersistenciaDTO comunicadoPersistencia;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Comunicado result;
  
 	public ConstruirPersistirComunicado (ComunicadoPersistenciaDTO comunicadoPersistencia){
 		super();
		this.comunicadoPersistencia=comunicadoPersistencia;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(comunicadoPersistencia == null ? null : Entity.json(comunicadoPersistencia));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Comunicado) response.readEntity(Comunicado.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Comunicado getResult() {
		return result;
	}

 
  
  	public void setComunicadoPersistencia (ComunicadoPersistenciaDTO comunicadoPersistencia){
 		this.comunicadoPersistencia=comunicadoPersistencia;
 	}
 	
 	public ComunicadoPersistenciaDTO getComunicadoPersistencia (){
 		return comunicadoPersistencia;
 	}
  
}