package com.asopagos.listaschequeo.clients;

import com.asopagos.enumeraciones.core.ClasificacionEnum;
import com.asopagos.enumeraciones.core.TipoTransaccionEnum;
import com.asopagos.listaschequeo.dto.RequisitosCajasCompensacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/requisitos/cajasCompensacion
 */
public class ConsultarRequisitosCajasCompensacion extends ServiceClient {
 
  
  	private ClasificacionEnum clasificacion;
  	private TipoTransaccionEnum tipoTransaccion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RequisitosCajasCompensacionDTO result;
  
 	public ConsultarRequisitosCajasCompensacion (ClasificacionEnum clasificacion,TipoTransaccionEnum tipoTransaccion){
 		super();
		this.clasificacion=clasificacion;
		this.tipoTransaccion=tipoTransaccion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("clasificacion", clasificacion)
						.queryParam("tipoTransaccion", tipoTransaccion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RequisitosCajasCompensacionDTO) response.readEntity(RequisitosCajasCompensacionDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RequisitosCajasCompensacionDTO getResult() {
		return result;
	}

 
  	public void setClasificacion (ClasificacionEnum clasificacion){
 		this.clasificacion=clasificacion;
 	}
 	
 	public ClasificacionEnum getClasificacion (){
 		return clasificacion;
 	}
  	public void setTipoTransaccion (TipoTransaccionEnum tipoTransaccion){
 		this.tipoTransaccion=tipoTransaccion;
 	}
 	
 	public TipoTransaccionEnum getTipoTransaccion (){
 		return tipoTransaccion;
 	}
  
}