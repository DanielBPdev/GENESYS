package com.asopagos.novedades.composite.clients;

import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.dto.ResultadoValidacionArchivoDTO;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/validarArchivoRespuesta
 */
public class ValidarArchivoRespuesta extends ServiceClient { 
   	private TipoArchivoRespuestaEnum tipoArchivo;
   	private CargueArchivoActualizacionDTO cargue;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private ResultadoValidacionArchivoDTO result;
  
 	public ValidarArchivoRespuesta (TipoArchivoRespuestaEnum tipoArchivo,CargueArchivoActualizacionDTO cargue){
 		super();
		this.tipoArchivo=tipoArchivo;
		this.cargue=cargue;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoArchivo", tipoArchivo)
			.request(MediaType.APPLICATION_JSON)
			.post(cargue == null ? null : Entity.json(cargue));
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

 
  	public void setTipoArchivo (TipoArchivoRespuestaEnum tipoArchivo){
 		this.tipoArchivo=tipoArchivo;
 	}
 	
 	public TipoArchivoRespuestaEnum getTipoArchivo (){
 		return tipoArchivo;
 	}
  
  	public void setCargue (CargueArchivoActualizacionDTO cargue){
 		this.cargue=cargue;
 	}
 	
 	public CargueArchivoActualizacionDTO getCargue (){
 		return cargue;
 	}
  
}