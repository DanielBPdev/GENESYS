package com.asopagos.afiliaciones.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.ProductoNoConformeDTO;
import java.lang.Long;
import java.lang.Boolean;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliaciones/{idSolicitudAfiliacion}/productoNoConforme
 */
public class ConsultarProductosNoConforme extends ServiceClient {
 
  	private Long idSolicitudAfiliacion;
  
  	private Boolean resuelto;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ProductoNoConformeDTO> result;
  
 	public ConsultarProductosNoConforme (Long idSolicitudAfiliacion,Boolean resuelto){
 		super();
		this.idSolicitudAfiliacion=idSolicitudAfiliacion;
		this.resuelto=resuelto;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitudAfiliacion", idSolicitudAfiliacion)
									.queryParam("resuelto", resuelto)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ProductoNoConformeDTO>) response.readEntity(new GenericType<List<ProductoNoConformeDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ProductoNoConformeDTO> getResult() {
		return result;
	}

 	public void setIdSolicitudAfiliacion (Long idSolicitudAfiliacion){
 		this.idSolicitudAfiliacion=idSolicitudAfiliacion;
 	}
 	
 	public Long getIdSolicitudAfiliacion (){
 		return idSolicitudAfiliacion;
 	}
  
  	public void setResuelto (Boolean resuelto){
 		this.resuelto=resuelto;
 	}
 	
 	public Boolean getResuelto (){
 		return resuelto;
 	}
  
}