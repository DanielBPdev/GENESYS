package com.asopagos.subsidiomonetario.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.subsidiomonetario.dto.BloqueoBeneficiarioCuotaMonetariaDTO;
import com.asopagos.subsidiomonetario.dto.ConsultaBeneficiarioBloqueadosDTO;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio POST
 * /rest/subsidioMonetario/consultarBeneficiariosBloqueados
 */
public class ConsultarBeneficiariosBloqueados extends ServiceClient { 
    	private ConsultaBeneficiarioBloqueadosDTO consulta;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BloqueoBeneficiarioCuotaMonetariaDTO> result;
  
 	public ConsultarBeneficiariosBloqueados (ConsultaBeneficiarioBloqueadosDTO consulta){
 		super();
		this.consulta=consulta;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
			.request(MediaType.APPLICATION_JSON)
			.post(consulta == null ? null : Entity.json(consulta));
		return response;
	}
	
	@Override
	protected void getResultData(Response response) {
		result = (List<BloqueoBeneficiarioCuotaMonetariaDTO>) response.readEntity(new GenericType<List<BloqueoBeneficiarioCuotaMonetariaDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	public List<BloqueoBeneficiarioCuotaMonetariaDTO> getResult() {
		return result;
	}

 
  
  	public void setConsulta (ConsultaBeneficiarioBloqueadosDTO consulta){
 		this.consulta=consulta;
 	}
 	
 	public ConsultaBeneficiarioBloqueadosDTO getConsulta (){
 		return consulta;
 	}
  
}