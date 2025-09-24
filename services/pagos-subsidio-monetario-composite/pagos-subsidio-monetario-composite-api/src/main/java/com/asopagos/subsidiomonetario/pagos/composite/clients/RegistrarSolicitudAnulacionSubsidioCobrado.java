package com.asopagos.subsidiomonetario.pagos.composite.clients;

import com.asopagos.subsidiomonetario.pagos.dto.SolicitudAnulacionSubsidioCobradoDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/PagosSubsidioMonetarioComposite/registrarSolicitudAnulacionSubsidioCobrado
 */
public class RegistrarSolicitudAnulacionSubsidioCobrado extends ServiceClient { 
    	private SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobrado;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private SolicitudAnulacionSubsidioCobradoDTO result;
  
 	public RegistrarSolicitudAnulacionSubsidioCobrado (SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobrado){
 		super();
		this.solicitudAnulacionSubsidioCobrado=solicitudAnulacionSubsidioCobrado;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(solicitudAnulacionSubsidioCobrado == null ? null : Entity.json(solicitudAnulacionSubsidioCobrado));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (SolicitudAnulacionSubsidioCobradoDTO) response.readEntity(SolicitudAnulacionSubsidioCobradoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public SolicitudAnulacionSubsidioCobradoDTO getResult() {
		return result;
	}

 
  
  	public void setSolicitudAnulacionSubsidioCobrado (SolicitudAnulacionSubsidioCobradoDTO solicitudAnulacionSubsidioCobrado){
 		this.solicitudAnulacionSubsidioCobrado=solicitudAnulacionSubsidioCobrado;
 	}
 	
 	public SolicitudAnulacionSubsidioCobradoDTO getSolicitudAnulacionSubsidioCobrado (){
 		return solicitudAnulacionSubsidioCobrado;
 	}
  
}