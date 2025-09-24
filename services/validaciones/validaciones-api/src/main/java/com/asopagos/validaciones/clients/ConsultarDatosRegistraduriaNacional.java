package com.asopagos.validaciones.clients;

import com.asopagos.dto.DatosBasicosIdentificacionDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.validaciones.dto.ParametrosRegistroCivilDTO;
import com.asopagos.validaciones.dto.ConsultaRegistroCivilDTO;


import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/empleadores/consultarDatosRegistraduriaNacional
 */
public class ConsultarDatosRegistraduriaNacional extends ServiceClient { 
	private ParametrosRegistroCivilDTO parametros;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ConsultaRegistroCivilDTO result;
  
 	public ConsultarDatosRegistraduriaNacional (ParametrosRegistroCivilDTO parametros){
 		super();
		this.parametros=parametros;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(parametros == null ? null : Entity.json(parametros));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ConsultaRegistroCivilDTO) response.readEntity(ConsultaRegistroCivilDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ConsultaRegistroCivilDTO getResult() {
		return result;
	}

	public ParametrosRegistroCivilDTO getParametros() {
		return this.parametros;
	}

	public void setParametros(ParametrosRegistroCivilDTO parametros) {
		this.parametros = parametros;
	}
  
}