package com.asopagos.afiliaciones.empleadores.web.composite.clients;

import com.asopagos.enumeraciones.ResultadoRegistroContactoEnum;
import com.asopagos.dto.DigitarInformacionContactoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/solicitudAfiliacionEmpleador/digitarInformacionContacto
 */
public class DigitarInformacionContacto extends ServiceClient { 
    	private DigitarInformacionContactoDTO entrada;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoRegistroContactoEnum result;
  
 	public DigitarInformacionContacto (DigitarInformacionContactoDTO entrada){
 		super();
		this.entrada=entrada;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(entrada == null ? null : Entity.json(entrada));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoRegistroContactoEnum) response.readEntity(ResultadoRegistroContactoEnum.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoRegistroContactoEnum getResult() {
		return result;
	}

 
  
  	public void setEntrada (DigitarInformacionContactoDTO entrada){
 		this.entrada=entrada;
 	}
 	
 	public DigitarInformacionContactoDTO getEntrada (){
 		return entrada;
 	}
  
}