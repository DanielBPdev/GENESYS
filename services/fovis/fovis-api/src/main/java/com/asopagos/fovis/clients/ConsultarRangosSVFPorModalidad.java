package com.asopagos.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.modelo.RangoTopeValorSFVModeloDTO;
import com.asopagos.enumeraciones.fovis.ModalidadEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/fovis/consultarRangosSVFPorModalidad
 */
public class ConsultarRangosSVFPorModalidad extends ServiceClient {
 
  
  	private ModalidadEnum modalidad;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RangoTopeValorSFVModeloDTO> result;
  
 	public ConsultarRangosSVFPorModalidad (ModalidadEnum modalidad){
 		super();
		this.modalidad=modalidad;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("modalidad", modalidad)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RangoTopeValorSFVModeloDTO>) response.readEntity(new GenericType<List<RangoTopeValorSFVModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RangoTopeValorSFVModeloDTO> getResult() {
		return result;
	}

 
  	public void setModalidad (ModalidadEnum modalidad){
 		this.modalidad=modalidad;
 	}
 	
 	public ModalidadEnum getModalidad (){
 		return modalidad;
 	}
  
}