package com.asopagos.subsidiomonetario.pagos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.subsidiomonetario.pagos.PagoSubsidioProgramadoDTO;
import java.lang.Long;
import java.lang.String;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/pagosSubsidioMonetario/obtenerPagosSubsidio/{idAdminSubsidio}
 */
public class ObtenerPagosSubsidio extends ServiceClient {
 
  	private Long idAdminSubsidio;
  
  	private String numeroRadicacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<PagoSubsidioProgramadoDTO> result;
  
 	public ObtenerPagosSubsidio (Long idAdminSubsidio,String numeroRadicacion){
 		super();
		this.idAdminSubsidio=idAdminSubsidio;
		this.numeroRadicacion=numeroRadicacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idAdminSubsidio", idAdminSubsidio)
									.queryParam("numeroRadicacion", numeroRadicacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<PagoSubsidioProgramadoDTO>) response.readEntity(new GenericType<List<PagoSubsidioProgramadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<PagoSubsidioProgramadoDTO> getResult() {
		return result;
	}

 	public void setIdAdminSubsidio (Long idAdminSubsidio){
 		this.idAdminSubsidio=idAdminSubsidio;
 	}
 	
 	public Long getIdAdminSubsidio (){
 		return idAdminSubsidio;
 	}
  
  	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  
}