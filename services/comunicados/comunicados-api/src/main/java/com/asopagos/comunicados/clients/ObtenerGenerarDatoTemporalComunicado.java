package com.asopagos.comunicados.clients;

import com.asopagos.comunicados.dto.InfoJsonTemporalDTO;
import com.asopagos.entidades.ccf.afiliaciones.DatoTemporalComunicado;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/comunicados/obtenerGenerarDatoTemporalComunicado
 */
public class ObtenerGenerarDatoTemporalComunicado extends ServiceClient { 
    	private InfoJsonTemporalDTO info;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DatoTemporalComunicado result;
  
 	public ObtenerGenerarDatoTemporalComunicado (InfoJsonTemporalDTO info){
 		super();
		this.info=info;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(info == null ? null : Entity.json(info));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (DatoTemporalComunicado) response.readEntity(DatoTemporalComunicado.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public DatoTemporalComunicado getResult() {
		return result;
	}

 
  
  	public void setInfo (InfoJsonTemporalDTO info){
 		this.info=info;
 	}
 	
 	public InfoJsonTemporalDTO getInfo (){
 		return info;
 	}
  
}