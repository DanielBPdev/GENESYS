package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.ResultadoValidacionArchivoRetiroDTO;
import com.asopagos.dto.InformacionArchivoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/validarCargarArchivoRetiroTarjetaAnibol
 */
public class ValidarCargarArchivoRetiroTarjetaAnibol extends ServiceClient { 
    	private InformacionArchivoDTO informacionArchivoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoRetiroDTO result;
  
 	public ValidarCargarArchivoRetiroTarjetaAnibol (InformacionArchivoDTO informacionArchivoDTO){
 		super();
		this.informacionArchivoDTO=informacionArchivoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(informacionArchivoDTO == null ? null : Entity.json(informacionArchivoDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (ResultadoValidacionArchivoRetiroDTO) response.readEntity(ResultadoValidacionArchivoRetiroDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public ResultadoValidacionArchivoRetiroDTO getResult() {
		return result;
	}

 
  
  	public void setInformacionArchivoDTO (InformacionArchivoDTO informacionArchivoDTO){
 		this.informacionArchivoDTO=informacionArchivoDTO;
 	}
 	
 	public InformacionArchivoDTO getInformacionArchivoDTO (){
 		return informacionArchivoDTO;
 	}
  
}