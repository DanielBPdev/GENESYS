package com.asopagos.afiliados.clients;

import com.asopagos.afiliados.dto.ActivacionAfiliadoDTO;
import com.asopagos.enumeraciones.personas.EstadoAfiliadoEnum;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/afiliados/consultarEstadoRolAfiliadoConEmpleador
 */
public class ConsultarEstadoRolAfiliadoConEmpleador extends ServiceClient { 
    	private ActivacionAfiliadoDTO datosAfiliado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private EstadoAfiliadoEnum result;
  
 	public ConsultarEstadoRolAfiliadoConEmpleador (ActivacionAfiliadoDTO datosAfiliado){
 		super();
		this.datosAfiliado=datosAfiliado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosAfiliado == null ? null : Entity.json(datosAfiliado));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (EstadoAfiliadoEnum) response.readEntity(EstadoAfiliadoEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public EstadoAfiliadoEnum getResult() {
		return result;
	}

 
  
  	public void setDatosAfiliado (ActivacionAfiliadoDTO datosAfiliado){
 		this.datosAfiliado=datosAfiliado;
 	}
 	
 	public ActivacionAfiliadoDTO getDatosAfiliado (){
 		return datosAfiliado;
 	}
  
}