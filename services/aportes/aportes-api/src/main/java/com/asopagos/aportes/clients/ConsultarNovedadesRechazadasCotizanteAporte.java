package com.asopagos.aportes.clients;

import com.asopagos.dto.aportes.NovedadCotizanteDTO;
import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarNovedadesRechazadasCotizanteAporte/{idRegistroDetallado}
 */
public class ConsultarNovedadesRechazadasCotizanteAporte extends ServiceClient { 
  	private Long idRegistroDetallado;
    	private List<String> tiposTransaccionNovedadesRechazadas;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<NovedadCotizanteDTO> result;
  
 	public ConsultarNovedadesRechazadasCotizanteAporte (Long idRegistroDetallado,List<String> tiposTransaccionNovedadesRechazadas){
 		super();
		this.idRegistroDetallado=idRegistroDetallado;
		this.tiposTransaccionNovedadesRechazadas=tiposTransaccionNovedadesRechazadas;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.resolveTemplate("idRegistroDetallado", idRegistroDetallado)
			.request(MediaType.APPLICATION_JSON)
			.post(tiposTransaccionNovedadesRechazadas == null ? null : Entity.json(tiposTransaccionNovedadesRechazadas));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<NovedadCotizanteDTO>) response.readEntity(new GenericType<List<NovedadCotizanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<NovedadCotizanteDTO> getResult() {
		return result;
	}

 	public void setIdRegistroDetallado (Long idRegistroDetallado){
 		this.idRegistroDetallado=idRegistroDetallado;
 	}
 	
 	public Long getIdRegistroDetallado (){
 		return idRegistroDetallado;
 	}
  
  
  	public void setTiposTransaccionNovedadesRechazadas (List<String> tiposTransaccionNovedadesRechazadas){
 		this.tiposTransaccionNovedadesRechazadas=tiposTransaccionNovedadesRechazadas;
 	}
 	
 	public List<String> getTiposTransaccionNovedadesRechazadas (){
 		return tiposTransaccionNovedadesRechazadas;
 	}
  
}