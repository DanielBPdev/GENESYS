package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.dto.subsidiomonetario.pagos.SolicitudAnulacionSubsidioCobradoModeloDTO;
import java.lang.Boolean;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/pagosSubsidioMonetario/solicitudAnulacionSubsidioCobrado/actualizar
 */
public class ActualizarSolicitudAnulacionSubsidioCobrado extends ServiceClient { 
    	private SolicitudAnulacionSubsidioCobradoModeloDTO solicitudAnulacionSubsidioCobradoModeloDTO;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private Boolean result;
  
 	public ActualizarSolicitudAnulacionSubsidioCobrado (SolicitudAnulacionSubsidioCobradoModeloDTO solicitudAnulacionSubsidioCobradoModeloDTO){
 		super();
		this.solicitudAnulacionSubsidioCobradoModeloDTO=solicitudAnulacionSubsidioCobradoModeloDTO;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudAnulacionSubsidioCobradoModeloDTO == null ? null : Entity.json(solicitudAnulacionSubsidioCobradoModeloDTO));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (Boolean) response.readEntity(Boolean.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public Boolean getResult() {
		return result;
	}

 
  
  	public void setSolicitudAnulacionSubsidioCobradoModeloDTO (SolicitudAnulacionSubsidioCobradoModeloDTO solicitudAnulacionSubsidioCobradoModeloDTO){
 		this.solicitudAnulacionSubsidioCobradoModeloDTO=solicitudAnulacionSubsidioCobradoModeloDTO;
 	}
 	
 	public SolicitudAnulacionSubsidioCobradoModeloDTO getSolicitudAnulacionSubsidioCobradoModeloDTO (){
 		return solicitudAnulacionSubsidioCobradoModeloDTO;
 	}
  
}