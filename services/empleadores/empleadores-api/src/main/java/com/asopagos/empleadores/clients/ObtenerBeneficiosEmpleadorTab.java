package com.asopagos.empleadores.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.BeneficioEmpleadorModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/empleadores/obtenerBeneficiosEmpleador/{idEmpleador}
 */
public class ObtenerBeneficiosEmpleadorTab extends ServiceClient {
 
  	private Long idEmpleador;
  
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<BeneficioEmpleadorModeloDTO> result;
  
 	public ObtenerBeneficiosEmpleadorTab (Long idEmpleador){
 		super();
		this.idEmpleador=idEmpleador;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
						.resolveTemplate("idEmpleador", idEmpleador)
									.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<BeneficioEmpleadorModeloDTO>) response.readEntity(new GenericType<List<BeneficioEmpleadorModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<BeneficioEmpleadorModeloDTO> getResult() {
		return result;
	}

 	public void setIdEmpleador (Long idEmpleador){
 		this.idEmpleador=idEmpleador;
 	}
 	
 	public Long getIdEmpleador (){
 		return idEmpleador;
 	}
  
  
}