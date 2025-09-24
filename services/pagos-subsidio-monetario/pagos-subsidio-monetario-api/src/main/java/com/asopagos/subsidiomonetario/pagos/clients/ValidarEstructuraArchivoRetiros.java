package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/validarEstructuraArchivoRetiros/{nombreUsuario}/{nombreTerceroPagador}
 */
public class ValidarEstructuraArchivoRetiros extends ServiceClient { 
  	private String nombreTerceroPagador;
  	private String nombreUsuario;
    	private InformacionArchivoDTO informacionArchivoDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoDTO result;
  
 	public ValidarEstructuraArchivoRetiros (String nombreTerceroPagador,String nombreUsuario,InformacionArchivoDTO informacionArchivoDTO){
 		super();
		this.nombreTerceroPagador=nombreTerceroPagador;
		this.nombreUsuario=nombreUsuario;
		this.informacionArchivoDTO=informacionArchivoDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("nombreTerceroPagador", nombreTerceroPagador)
			.resolveTemplate("nombreUsuario", nombreUsuario)
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

 	public void setNombreTerceroPagador (String nombreTerceroPagador){
 		this.nombreTerceroPagador=nombreTerceroPagador;
 	}
 	
 	public String getNombreTerceroPagador (){
 		return nombreTerceroPagador;
 	}
  	public void setNombreUsuario (String nombreUsuario){
 		this.nombreUsuario=nombreUsuario;
 	}
 	
 	public String getNombreUsuario (){
 		return nombreUsuario;
 	}
  
  
  	public void setInformacionArchivoDTO (InformacionArchivoDTO informacionArchivoDTO){
 		this.informacionArchivoDTO=informacionArchivoDTO;
 	}
 	
 	public InformacionArchivoDTO getInformacionArchivoDTO (){
 		return informacionArchivoDTO;
 	}
  
}