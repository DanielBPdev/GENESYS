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
 * /rest/subsidioMonetario/buscarBeneficiariosAfiliado/condicionesLiquidacionSubsidio/{idSolicitud}/{idPersona}
 */
public class ConsultarBeneficiariosAfiliadoCondicionesLiquidacion extends ServiceClient {
 
  	private Long idPersona;
  	private Long idSolicitud;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BeneficiariosAfiliadoDTO> result;
  
 	public ConsultarBeneficiariosAfiliadoCondicionesLiquidacion (Long idPersona,Long idSolicitud){
 		super();
		this.idPersona=idPersona;
		this.idSolicitud=idSolicitud;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idPersona", idPersona)
						.resolveTemplate("idSolicitud", idSolicitud)
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
  	public void setIdSolicitud (Long idSolicitud){
 		this.idSolicitud=idSolicitud;
 	}
 	
 	public Long getIdSolicitud (){
 		return idSolicitud;
 	}
  
  
}