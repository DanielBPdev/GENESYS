package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.subsidiomonetario.liquidacion.BeneficiariosAfiliadoDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/subsidioMonetario/buscarBeneficiariosAfiliado/{idPersona}
 */
public class ConsultarBeneficiariosAfiliado extends ServiceClient {
 
  	private Long idPersona;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BeneficiariosAfiliadoDTO> result;
  
 	public ConsultarBeneficiariosAfiliado (Long idPersona){
 		super();
		this.idPersona=idPersona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idPersona", idPersona)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<BeneficiariosAfiliadoDTO>) response.readEntity(new GenericType<List<BeneficiariosAfiliadoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<BeneficiariosAfiliadoDTO> getResult() {
		return result;
	}

 	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  
  
}