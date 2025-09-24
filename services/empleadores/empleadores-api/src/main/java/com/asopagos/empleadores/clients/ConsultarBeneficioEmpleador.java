package com.asopagos.empleadores.clients;

import java.lang.Long;
import com.asopagos.enumeraciones.afiliaciones.TipoBeneficioEnum;
import com.asopagos.dto.modelo.BeneficioEmpleadorModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empleadores/{idEmpleador}/consultarBeneficioEmpleador
 */
public class ConsultarBeneficioEmpleador extends ServiceClient {
 
  	private Long idEmpleador;
  
  	private TipoBeneficioEnum tipoBeneficio;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private BeneficioEmpleadorModeloDTO result;
  
 	public ConsultarBeneficioEmpleador (Long idEmpleador,TipoBeneficioEnum tipoBeneficio){
 		super();
		this.idEmpleador=idEmpleador;
		this.tipoBeneficio=tipoBeneficio;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEmpleador", idEmpleador)
									.queryParam("tipoBeneficio", tipoBeneficio)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (BeneficioEmpleadorModeloDTO) response.readEntity(BeneficioEmpleadorModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public BeneficioEmpleadorModeloDTO getResult() {
		return result;
	}

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  	public void setTipoBeneficio (TipoBeneficioEnum tipoBeneficio){
 		this.tipoBeneficio=tipoBeneficio;
 	}
 	
 	public TipoBeneficioEnum getTipoBeneficio (){
 		return tipoBeneficio;
 	}
  
}