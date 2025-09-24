package com.asopagos.cartera.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.cartera.dto.BitacoraCarteraDTO;
import com.asopagos.dto.modelo.SolicitudPreventivaModeloDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/cartera/consultarBitacoraPreventiva360
 */
public class ConsultarBitacoraPreventiva360 extends ServiceClient { 
    	private SolicitudPreventivaModeloDTO preventivaDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BitacoraCarteraDTO> result;
  
 	public ConsultarBitacoraPreventiva360 (SolicitudPreventivaModeloDTO preventivaDTO){
 		super();
		this.preventivaDTO=preventivaDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(preventivaDTO == null ? null : Entity.json(preventivaDTO));
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

 
  
  	public void setPreventivaDTO (SolicitudPreventivaModeloDTO preventivaDTO){
 		this.preventivaDTO=preventivaDTO;
 	}
 	
 	public SolicitudPreventivaModeloDTO getPreventivaDTO (){
 		return preventivaDTO;
 	}
  
}