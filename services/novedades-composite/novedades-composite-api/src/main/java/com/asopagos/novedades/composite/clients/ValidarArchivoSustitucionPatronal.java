package com.asopagos.novedades.composite.clients;

import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import com.asopagos.dto.CargueArchivoActualizacionDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesMasivasComposite/validarArchivoSustitucionPatronal
 */
public class ValidarArchivoSustitucionPatronal extends ServiceClient { 
   	private TipoArchivoRespuestaEnum tipoArchivo;
   	private CargueArchivoActualizacionDTO cargue;
  
  
 	public ValidarArchivoSustitucionPatronal (TipoArchivoRespuestaEnum tipoArchivo,CargueArchivoActualizacionDTO cargue){
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