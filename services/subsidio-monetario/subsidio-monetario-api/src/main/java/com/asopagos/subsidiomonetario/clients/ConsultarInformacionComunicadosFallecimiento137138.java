package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.String;
import com.asopagos.subsidiomonetario.dto.DatosComunicadoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/consultarInformacionComunicados/fallecimiento137_138/{numeroRadicacion}/{causal}
 */
public class ConsultarInformacionComunicadosFallecimiento137138 extends ServiceClient {
 
  	private String numeroRadicacion;
  	private Long causal;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DatosComunicadoDTO> result;
  
 	public ConsultarInformacionComunicadosFallecimiento137138 (String numeroRadicacion,Long causal){
 		super();
		this.numeroRadicacion=numeroRadicacion;
		this.causal=causal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("numeroRadicacion", numeroRadicacion)
						.resolveTemplate("causal", causal)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DatosComunicadoDTO>) response.readEntity(new GenericType<List<DatosComunicadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DatosComunicadoDTO> getResult() {
		return result;
	}

 	public void setNumeroRadicacion (String numeroRadicacion){
 		this.numeroRadicacion=numeroRadicacion;
 	}
 	
 	public String getNumeroRadicacion (){
 		return numeroRadicacion;
 	}
  	public void setCausal (Long causal){
 		this.causal=causal;
 	}
 	
 	public Long getCausal (){
 		return causal;
 	}
  
  
}