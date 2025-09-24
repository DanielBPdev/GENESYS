package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.aportes.dto.DetalleDevolucionCotizanteDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/aportes/consultarDetalleDevolucionCotizantes
 */
public class ConsultarDetalleDevolucionCotizantes extends ServiceClient { 
    	private List<DetalleDevolucionCotizanteDTO> datosCotizante;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DetalleDevolucionCotizanteDTO> result;
  
 	public ConsultarDetalleDevolucionCotizantes (List<DetalleDevolucionCotizanteDTO> datosCotizante){
 		super();
		this.datosCotizante=datosCotizante;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(datosCotizante == null ? null : Entity.json(datosCotizante));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<DetalleDevolucionCotizanteDTO>) response.readEntity(new GenericType<List<DetalleDevolucionCotizanteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<DetalleDevolucionCotizanteDTO> getResult() {
		return result;
	}

 
  
  	public void setDatosCotizante (List<DetalleDevolucionCotizanteDTO> datosCotizante){
 		this.datosCotizante=datosCotizante;
 	}
 	
 	public List<DetalleDevolucionCotizanteDTO> getDatosCotizante (){
 		return datosCotizante;
 	}
  
}