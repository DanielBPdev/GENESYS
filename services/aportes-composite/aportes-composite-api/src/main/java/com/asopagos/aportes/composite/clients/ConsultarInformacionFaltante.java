package com.asopagos.aportes.composite.clients;

import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.lang.Long;
import com.asopagos.aportes.composite.dto.GestionInformacionFaltanteDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aporteManual/{idSolicitud}/consultarInformacionFaltante
 */
public class ConsultarInformacionFaltante extends ServiceClient {
 
  	private Long idSolicitud;
  
  	private ProcesoEnum proceso;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private GestionInformacionFaltanteDTO result;
  
 	public ConsultarInformacionFaltante (Long idSolicitud,ProcesoEnum proceso){
 		super();
		this.idSolicitud=idSolicitud;
		this.proceso=proceso;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idSolicitud", idSolicitud)
									.queryParam("proceso", proceso)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (GestionInformacionFaltanteDTO) response.readEntity(GestionInformacionFaltanteDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public GestionInformacionFaltanteDTO getResult() {
		return result;
	}

 	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  
}