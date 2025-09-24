package com.asopagos.subsidiomonetario.pagos.clients;

import com.asopagos.subsidiomonetario.pagos.dto.DetalleSolicitudAnulacionSubsidioCobradoDTO;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/solicitudAnulacionSubsidioCobrado/consultar/{numeroRadicacion}
 */
public class ConsultarDetalleSolicitudAnulacionSubsidioCobrado extends ServiceClient {
 
  	private String numeroRadicacion;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private DetalleSolicitudAnulacionSubsidioCobradoDTO result;
  
 	public ConsultarDetalleSolicitudAnulacionSubsidioCobrado (String numeroRadicacion){
 		super();
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (DetalleSolicitudAnulacionSubsidioCobradoDTO) response.readEntity(DetalleSolicitudAnulacionSubsidioCobradoDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public DetalleSolicitudAnulacionSubsidioCobradoDTO getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
  
}