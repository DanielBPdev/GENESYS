package com.asopagos.novedades.clients;

import com.asopagos.dto.modelo.ParametrizacionNovedadModeloDTO;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/consultarNovedad
 */
public class ConsultarNovedadPorNombre extends ServiceClient {
 
  
  	private TipoTransaccionEnum tipoTransaccion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ParametrizacionNovedadModeloDTO result;
  
 	public ConsultarNovedadPorNombre (TipoTransaccionEnum tipoTransaccion){
 		super();
		this.tipoTransaccion=tipoTransaccion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoTransaccion", tipoTransaccion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (ParametrizacionNovedadModeloDTO) response.readEntity(ParametrizacionNovedadModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public ParametrizacionNovedadModeloDTO getResult() {
		return result;
	}

 
  	public void setTipoTransaccion (TipoTransaccionEnum tipoTransaccion){
 		this.tipoTransaccion=tipoTransaccion;
 	}
 	
 	public TipoTransaccionEnum getTipoTransaccion (){
 		return tipoTransaccion;
 	}
  
}