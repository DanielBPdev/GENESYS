package com.asopagos.afiliados.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.BeneficiarioModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/afiliados/{idBeneficiario}/consultarBeneficiario
 */
public class ConsultarBeneficiario extends ServiceClient {
 
  	private Long idBeneficiario;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private BeneficiarioModeloDTO result;
  
 	public ConsultarBeneficiario (Long idBeneficiario){
 		super();
		this.idBeneficiario=idBeneficiario;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idBeneficiario", idBeneficiario)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (BeneficiarioModeloDTO) response.readEntity(BeneficiarioModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public BeneficiarioModeloDTO getResult() {
		return result;
	}

 	public void setIdBeneficiario (Long idBeneficiario){
 		this.idBeneficiario=idBeneficiario;
 	}
 	
 	public Long getIdBeneficiario (){
 		return idBeneficiario;
 	}
  
  
}