package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.CertificadoEscolarBeneficiarioDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/consultarCertificadoEscolarVigentePersona
 */
public class ConsultarCertificadoEscolarVigentePersona extends ServiceClient {
 
  
  	private Long idPersona;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private CertificadoEscolarBeneficiarioDTO result;
  
 	public ConsultarCertificadoEscolarVigentePersona (Long idPersona){
 		super();
		this.idPersona=idPersona;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPersona", idPersona)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (CertificadoEscolarBeneficiarioDTO) response.readEntity(CertificadoEscolarBeneficiarioDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public CertificadoEscolarBeneficiarioDTO getResult() {
		return result;
	}

 
  	public void setIdPersona (Long idPersona){
 		this.idPersona=idPersona;
 	}
 	
 	public Long getIdPersona (){
 		return idPersona;
 	}
  
}