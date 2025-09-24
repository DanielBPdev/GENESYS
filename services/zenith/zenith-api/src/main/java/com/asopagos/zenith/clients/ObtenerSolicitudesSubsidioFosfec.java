package com.asopagos.zenith.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.ConsultaSubsidioFosfecDTO;
import com.asopagos.dto.SolicitudSubsidioFosfecDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/externalAPI/clienteZenith/obtenerSolicitudesSubsidioFosfec
 */
public class ObtenerSolicitudesSubsidioFosfec extends ServiceClient { 
    	private ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<SolicitudSubsidioFosfecDTO> result;
  
 	public ObtenerSolicitudesSubsidioFosfec (ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO){
 		super();
		this.consultaSubsidioFosfecDTO=consultaSubsidioFosfecDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(consultaSubsidioFosfecDTO == null ? null : Entity.json(consultaSubsidioFosfecDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<SolicitudSubsidioFosfecDTO>) response.readEntity(new GenericType<List<SolicitudSubsidioFosfecDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<SolicitudSubsidioFosfecDTO> getResult() {
		return result;
	}

 
  
  	public void setConsultaSubsidioFosfecDTO (ConsultaSubsidioFosfecDTO consultaSubsidioFosfecDTO){
 		this.consultaSubsidioFosfecDTO=consultaSubsidioFosfecDTO;
 	}
 	
 	public ConsultaSubsidioFosfecDTO getConsultaSubsidioFosfecDTO (){
 		return consultaSubsidioFosfecDTO;
 	}
  
}