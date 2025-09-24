package com.asopagos.afiliados.clients;

import javax.ws.rs.core.GenericType;
import com.asopagos.dto.modelo.TipoInfraestructuraModeloDTO;
import java.util.List;
import java.lang.Boolean;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarTiposInfraestructura
 */
public class ConsultarTiposInfraestructura extends ServiceClient {
 
  
  	private Boolean estado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<TipoInfraestructuraModeloDTO> result;
  
 	public ConsultarTiposInfraestructura (Boolean estado){
 		super();
		this.estado=estado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("estado", estado)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<TipoInfraestructuraModeloDTO>) response.readEntity(new GenericType<List<TipoInfraestructuraModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<TipoInfraestructuraModeloDTO> getResult() {
		return result;
	}

 
  	public void setEstado (Boolean estado){
 		this.estado=estado;
 	}
 	
 	public Boolean getEstado (){
 		return estado;
 	}
  
}