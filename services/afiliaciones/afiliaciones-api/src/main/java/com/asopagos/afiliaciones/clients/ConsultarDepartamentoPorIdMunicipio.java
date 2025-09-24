package com.asopagos.afiliaciones.clients;

import java.lang.Short;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.asopagos.entidades.transversal.core.Departamento;


import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/ConsultarDepartamentoPorIdMunicipio/{codigoMunicipio}
 */
public class ConsultarDepartamentoPorIdMunicipio extends ServiceClient {
 
  	private Short codigoMunicipio;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Departamento result;
  
 	public ConsultarDepartamentoPorIdMunicipio (Short codigoMunicipio){
 		super();
		this.codigoMunicipio=codigoMunicipio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("codigoMunicipio", codigoMunicipio)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (Departamento) response.readEntity(Departamento.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public Departamento getResult() {
		return result;
	}

 	public void setCodigoMunicipio (Short codigoMunicipio){
 		this.codigoMunicipio=codigoMunicipio;
 	}
 	
 	public Short getCodigoMunicipio (){
 		return codigoMunicipio;
 	}
  
  
}