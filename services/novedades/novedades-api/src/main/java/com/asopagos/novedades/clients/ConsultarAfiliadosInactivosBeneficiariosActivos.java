package com.asopagos.novedades.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.novedades.dto.DatosAfiliadoRetiroDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedades/consultarAfiliadosInactivosBeneficiariosActivos
 */
public class ConsultarAfiliadosInactivosBeneficiariosActivos extends ServiceClient {
 
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DatosAfiliadoRetiroDTO> result;
  
 	public ConsultarAfiliadosInactivosBeneficiariosActivos (){
 		super();
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DatosAfiliadoRetiroDTO>) response.readEntity(new GenericType<List<DatosAfiliadoRetiroDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DatosAfiliadoRetiroDTO> getResult() {
		return result;
	}

 
  
}