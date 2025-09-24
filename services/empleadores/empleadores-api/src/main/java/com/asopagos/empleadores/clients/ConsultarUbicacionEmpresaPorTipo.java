package com.asopagos.empleadores.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.core.TipoUbicacionEnum;
import java.lang.Long;
import com.asopagos.dto.UbicacionDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empleadores/consultarUbicacionEmpresaPorTipo/{idPersona}
 */
public class ConsultarUbicacionEmpresaPorTipo extends ServiceClient {
 
  	private Long idPersona;
  
  	private List<TipoUbicacionEnum> tipoUbicacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<UbicacionDTO> result;
  
 	public ConsultarUbicacionEmpresaPorTipo (Long idPersona,List<TipoUbicacionEnum> tipoUbicacion){
 		super();
		this.idPersona=idPersona;
		this.tipoUbicacion=tipoUbicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idPersona", idPersona)
									.queryParam("tipoUbicacion", tipoUbicacion.toArray())
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<UbicacionDTO>) response.readEntity(new GenericType<List<UbicacionDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<UbicacionDTO> getResult() {
		return result;
	}

 	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  
  	public void setTipoUbicacion (List<TipoUbicacionEnum> tipoUbicacion){
 		this.tipoUbicacion=tipoUbicacion;
 	}
 	
 	public List<TipoUbicacionEnum> getTipoUbicacion (){
 		return tipoUbicacion;
 	}
  
}