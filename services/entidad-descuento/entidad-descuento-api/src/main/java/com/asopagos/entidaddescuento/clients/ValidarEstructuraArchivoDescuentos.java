package com.asopagos.entidaddescuento.clients;

import java.lang.Long;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/entidadDescuento/validarEstructuraArchivoDescuentos/{idTrazabilidad}
 */
public class ValidarEstructuraArchivoDescuentos extends ServiceClient {
  	private Long idTrazabilidad;
    	private InformacionArchivoDTO informacionArchivoDTO;

  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoDTO result;

 	public ValidarEstructuraArchivoDescuentos (Long idTrazabilidad,InformacionArchivoDTO informacionArchivoDTO){
 		super();
		this.idTrazabilidad=idTrazabilidad;
		this.informacionArchivoDTO=informacionArchivoDTO;
 	}

 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idTrazabilidad", idTrazabilidad)
			.request(MediaType.APPLICATION_JSON)
			.post(informacionArchivoDTO == null ? null : Entity.json(informacionArchivoDTO));
		return response;
	}

	@Override
	protected void getResultData(Response response) {
		result = (ResultadoValidacionArchivoDTO) response.readEntity(ResultadoValidacionArchivoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoValidacionArchivoDTO getResult() {
		return result;
	}

 	public void setIdTrazabilidad (Long idTrazabilidad){
 		this.idTrazabilidad=idTrazabilidad;
 	}

 	public Long getIdTrazabilidad (){
 		return idTrazabilidad;
 	}

  
  	public void setInformacionArchivoDTO (InformacionArchivoDTO informacionArchivoDTO){
 		this.informacionArchivoDTO=informacionArchivoDTO;
 	}
 	
 	public InformacionArchivoDTO getInformacionArchivoDTO (){
 		return informacionArchivoDTO;
 	}
}