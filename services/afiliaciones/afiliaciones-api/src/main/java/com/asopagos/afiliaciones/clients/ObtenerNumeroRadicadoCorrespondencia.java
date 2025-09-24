package com.asopagos.afiliaciones.clients;

import com.asopagos.dto.NumeroRadicadoCorrespondenciaDTO;
import com.asopagos.enumeraciones.core.TipoEtiquetaEnum;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/obtenerNumeroRadicadoCorrespondencia
 */
public class ObtenerNumeroRadicadoCorrespondencia extends ServiceClient {
 
  
  	private Integer cantidad;
  	private TipoEtiquetaEnum tipoEtiqueta;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private NumeroRadicadoCorrespondenciaDTO result;
  
 	public ObtenerNumeroRadicadoCorrespondencia (Integer cantidad,TipoEtiquetaEnum tipoEtiqueta){
 		super();
		this.cantidad=cantidad;
		this.tipoEtiqueta=tipoEtiqueta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("cantidad", cantidad)
						.queryParam("tipoEtiqueta", tipoEtiqueta)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (NumeroRadicadoCorrespondenciaDTO) response.readEntity(NumeroRadicadoCorrespondenciaDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public NumeroRadicadoCorrespondenciaDTO getResult() {
		return result;
	}

 
  	public void setCantidad (Integer cantidad){
 		this.cantidad=cantidad;
 	}
 	
 	public Integer getCantidad (){
 		return cantidad;
 	}
  	public void setTipoEtiqueta (TipoEtiquetaEnum tipoEtiqueta){
 		this.tipoEtiqueta=tipoEtiqueta;
 	}
 	
 	public TipoEtiquetaEnum getTipoEtiqueta (){
 		return tipoEtiqueta;
 	}
  
}