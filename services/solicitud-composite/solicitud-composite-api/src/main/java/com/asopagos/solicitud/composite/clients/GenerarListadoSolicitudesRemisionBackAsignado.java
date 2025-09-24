package com.asopagos.solicitud.composite.clients;

import com.asopagos.dto.afiliaciones.RemisionBackDTO;
import com.asopagos.enumeraciones.core.ProcesoEnum;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/solicitudComposite/remisionBackAsignado
 */
public class GenerarListadoSolicitudesRemisionBackAsignado extends ServiceClient {
 
  
  	private Long fechaInicial;
  	private ProcesoEnum proceso;
  	private Long fechaFinal;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RemisionBackDTO result;
  
 	public GenerarListadoSolicitudesRemisionBackAsignado (Long fechaInicial,ProcesoEnum proceso,Long fechaFinal){
 		super();
		this.fechaInicial=fechaInicial;
		this.proceso=proceso;
		this.fechaFinal=fechaFinal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("fechaInicial", fechaInicial)
						.queryParam("proceso", proceso)
						.queryParam("fechaFinal", fechaFinal)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RemisionBackDTO) response.readEntity(RemisionBackDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RemisionBackDTO getResult() {
		return result;
	}

 
  	public void setFechaInicial (Long fechaInicial){
 		this.fechaInicial=fechaInicial;
 	}
 	
 	public Long getFechaInicial (){
 		return fechaInicial;
 	}
  	public void setProceso (ProcesoEnum proceso){
 		this.proceso=proceso;
 	}
 	
 	public ProcesoEnum getProceso (){
 		return proceso;
 	}
  	public void setFechaFinal (Long fechaFinal){
 		this.fechaFinal=fechaFinal;
 	}
 	
 	public Long getFechaFinal (){
 		return fechaFinal;
 	}
  
}