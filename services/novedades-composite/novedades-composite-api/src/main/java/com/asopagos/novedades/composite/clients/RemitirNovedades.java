package com.asopagos.novedades.composite.clients;

import com.asopagos.dto.cargaMultiple.InformacionActualizacionNovedadDTO;
import java.util.List;
import java.lang.Long;
import com.asopagos.enumeraciones.TipoArchivoRespuestaEnum;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/novedadesComposite/remitirNovedades
 */
public class RemitirNovedades extends ServiceClient { 
   	private TipoArchivoRespuestaEnum tipoArchivo;
  	private String usuarioDestino;
  	private Long codigoCargue;
   	private List<InformacionActualizacionNovedadDTO> listActualizacionInfoNovedad;
  
  
 	public RemitirNovedades (TipoArchivoRespuestaEnum tipoArchivo,String usuarioDestino,Long codigoCargue,List<InformacionActualizacionNovedadDTO> listActualizacionInfoNovedad){
 		super();
		this.tipoArchivo=tipoArchivo;
		this.usuarioDestino=usuarioDestino;
		this.codigoCargue=codigoCargue;
		this.listActualizacionInfoNovedad=listActualizacionInfoNovedad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.queryParam("tipoArchivo", tipoArchivo)
			.queryParam("usuarioDestino", usuarioDestino)
			.queryParam("codigoCargue", codigoCargue)
			.request(MediaType.APPLICATION_JSON)
			.post(listActualizacionInfoNovedad == null ? null : Entity.json(listActualizacionInfoNovedad));
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
  	public void setUsuarioDestino (String usuarioDestino){
 		this.usuarioDestino=usuarioDestino;
 	}
 	
 	public String getUsuarioDestino (){
 		return usuarioDestino;
 	}
  	public void setCodigoCargue (Long codigoCargue){
 		this.codigoCargue=codigoCargue;
 	}
 	
 	public Long getCodigoCargue (){
 		return codigoCargue;
 	}
  
  	public void setListActualizacionInfoNovedad (List<InformacionActualizacionNovedadDTO> listActualizacionInfoNovedad){
 		this.listActualizacionInfoNovedad=listActualizacionInfoNovedad;
 	}
 	
 	public List<InformacionActualizacionNovedadDTO> getListActualizacionInfoNovedad (){
 		return listActualizacionInfoNovedad;
 	}
  
}