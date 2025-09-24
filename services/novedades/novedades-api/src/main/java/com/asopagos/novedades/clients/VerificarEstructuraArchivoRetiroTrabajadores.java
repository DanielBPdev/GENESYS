package com.asopagos.novedades.clients;

import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesCargueMultiple/verificarEstructuraArchivoRetiroTrabajadores/{tipoArchivoRespuesta}
 */
public class VerificarEstructuraArchivoRetiroTrabajadores extends ServiceClient { 
  	private TipoArchivoRespuestaEnum tipoArchivoRespuesta;
    	private InformacionArchivoDTO archivo;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoDTO result;
  
 	public VerificarEstructuraArchivoRetiroTrabajadores (TipoArchivoRespuestaEnum tipoArchivoRespuesta,InformacionArchivoDTO archivo){
 		super();
		this.tipoArchivoRespuesta=tipoArchivoRespuesta;
		this.archivo=archivo;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("tipoArchivoRespuesta", tipoArchivoRespuesta)
			.request(MediaType.APPLICATION_JSON)
			.post(archivo == null ? null : Entity.json(archivo));
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

 	public void setTipoArchivoRespuesta (TipoArchivoRespuestaEnum tipoArchivoRespuesta){
 		this.tipoArchivoRespuesta=tipoArchivoRespuesta;
 	}
 	
 	public TipoArchivoRespuestaEnum getTipoArchivoRespuesta (){
 		return tipoArchivoRespuesta;
 	}
  
  
  	public void setArchivo (InformacionArchivoDTO archivo){
 		this.archivo=archivo;
 	}
 	
 	public InformacionArchivoDTO getArchivo (){
 		return archivo;
 	}
  
}