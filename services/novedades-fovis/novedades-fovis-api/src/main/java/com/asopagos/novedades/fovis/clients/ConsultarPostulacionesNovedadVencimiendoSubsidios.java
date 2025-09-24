package com.asopagos.novedades.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.enumeraciones.fovis.EstadoHogarEnum;
import com.asopagos.dto.modelo.PostulacionFOVISModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedadesFovis/consultarPostulacionesNovedadVencimiendoSubsidios
 */
public class ConsultarPostulacionesNovedadVencimiendoSubsidios extends ServiceClient {
 
  
  	private EstadoHogarEnum estadoHogar;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PostulacionFOVISModeloDTO> result;
  
 	public ConsultarPostulacionesNovedadVencimiendoSubsidios (EstadoHogarEnum estadoHogar){
 		super();
		this.estadoHogar=estadoHogar;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("estadoHogar", estadoHogar)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PostulacionFOVISModeloDTO>) response.readEntity(new GenericType<List<PostulacionFOVISModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PostulacionFOVISModeloDTO> getResult() {
		return result;
	}

 
  	public void setEstadoHogar (EstadoHogarEnum estadoHogar){
 		this.estadoHogar=estadoHogar;
 	}
 	
 	public EstadoHogarEnum getEstadoHogar (){
 		return estadoHogar;
 	}
  
}