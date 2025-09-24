package com.asopagos.subsidiomonetario.clients;

import java.util.List;
import com.asopagos.subsidiomonetario.dto.BloqueoBeneficiarioCuotaMonetariaDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/desbloquearBeneficiariosCM
 */
public class DesbloquearBeneficiariosCM extends ServiceClient { 
    	private List<BloqueoBeneficiarioCuotaMonetariaDTO> beneficiariosBloqueados;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private int result;
  
 	public DesbloquearBeneficiariosCM (List<BloqueoBeneficiarioCuotaMonetariaDTO> beneficiariosBloqueados){
 		super();
		this.beneficiariosBloqueados=beneficiariosBloqueados;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(beneficiariosBloqueados == null ? null : Entity.json(beneficiariosBloqueados));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (int) response.readEntity(int.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public int getResult() {
		return result;
	}

 
  
  	public void setBeneficiariosBloqueados (List<BloqueoBeneficiarioCuotaMonetariaDTO> beneficiariosBloqueados){
 		this.beneficiariosBloqueados=beneficiariosBloqueados;
 	}
 	
 	public List<BloqueoBeneficiarioCuotaMonetariaDTO> getBeneficiariosBloqueados (){
 		return beneficiariosBloqueados;
 	}
  
}