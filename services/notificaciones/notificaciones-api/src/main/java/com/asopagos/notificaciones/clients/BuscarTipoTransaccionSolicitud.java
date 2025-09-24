package com.asopagos.notificaciones.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/enviarCorreo/buscarTipoTransaccionSolicitud
 */
public class BuscarTipoTransaccionSolicitud extends ServiceClient { 
    	private Long idSolicitud;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private TipoTransaccionEnum result;
  
 	public BuscarTipoTransaccionSolicitud (Long idSolicitud){
 		super();
		this.idSolicitud=idSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(idSolicitud == null ? null : Entity.json(idSolicitud));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (TipoTransaccionEnum) response.readEntity(TipoTransaccionEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public TipoTransaccionEnum getResult() {
		return result;
	}

 
  
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
}