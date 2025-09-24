package com.asopagos.aportes.clients;

import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/{idRegistroDetallado}/{idPersona}/consultarNovedadRetiro
 */
public class ConsultarNovedadRetiro extends ServiceClient {
 
  	private Long idPersona;
  	private Long idRegistroDetallado;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<NovedadCotizanteDTO> result;
  
 	public ConsultarNovedadRetiro (Long idPersona,Long idRegistroDetallado){
 		super();
		this.idPersona=idPersona;
		this.idRegistroDetallado=idRegistroDetallado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idPersona", idPersona)
						.resolveTemplate("idRegistroDetallado", idRegistroDetallado)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<NovedadCotizanteDTO>) response.readEntity(new GenericType<List<NovedadCotizanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<NovedadCotizanteDTO> getResult() {
		return result;
	}

 	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  	public void setIdRegistroDetallado (Long idRegistroDetallado){
 		this.idRegistroDetallado=idRegistroDetallado;
 	}
 	
 	public Long getIdRegistroDetallado (){
 		return idRegistroDetallado;
 	}
  
  
}