// ===================================== Certificados masivos
package com.asopagos.novedades.composite.clients;

import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.dto.InformacionArchivoDTO;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;


public class VerificarEstructuraArchivoCertificadosMasivos  extends ServiceClient{

    private TipoArchivoRespuestaEnum tipoArchivoRespuesta;
	private InformacionArchivoDTO archivo;
	private Long idEmpleaador;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoDTO result;
  
 	public VerificarEstructuraArchivoCertificadosMasivos(TipoArchivoRespuestaEnum tipoArchivoRespuesta,InformacionArchivoDTO archivo,Long idEmpleaador){
 		super();
		this.tipoArchivoRespuesta=tipoArchivoRespuesta;
		this.archivo=archivo;
		this.idEmpleaador = idEmpleaador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
            .resolveTemplate("tipoArchivoRespuesta", tipoArchivoRespuesta)
			.resolveTemplate("idEmpleador", idEmpleaador)
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