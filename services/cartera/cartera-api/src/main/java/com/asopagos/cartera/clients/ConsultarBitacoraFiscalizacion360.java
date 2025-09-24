package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.SolicitudFiscalizacionModeloDTO;
import com.asopagos.cartera.dto.BitacoraCarteraDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarBitacoraFiscalizacion360
 */
public class ConsultarBitacoraFiscalizacion360 extends ServiceClient { 
    	private SolicitudFiscalizacionModeloDTO fiscalizacionDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BitacoraCarteraDTO> result;
  
 	public ConsultarBitacoraFiscalizacion360 (SolicitudFiscalizacionModeloDTO fiscalizacionDTO){
 		super();
		this.fiscalizacionDTO=fiscalizacionDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(fiscalizacionDTO == null ? null : Entity.json(fiscalizacionDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<BitacoraCarteraDTO>) response.readEntity(new GenericType<List<BitacoraCarteraDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<BitacoraCarteraDTO> getResult() {
		return result;
	}

 
  
  	public void setFiscalizacionDTO (SolicitudFiscalizacionModeloDTO fiscalizacionDTO){
 		this.fiscalizacionDTO=fiscalizacionDTO;
 	}
 	
 	public SolicitudFiscalizacionModeloDTO getFiscalizacionDTO (){
 		return fiscalizacionDTO;
 	}
  
}