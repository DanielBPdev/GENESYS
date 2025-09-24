package com.asopagos.legalizacionfovis.clients;

import com.asopagos.dto.modelo.JefeHogarModeloDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarDatosJefeHogarPostulacion
 */
public class ConsultarDatosJefeHogarPostulacion extends ServiceClient {
 
  
  	private Long idPostulacionFovis;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private JefeHogarModeloDTO result;
  
 	public ConsultarDatosJefeHogarPostulacion (Long idPostulacionFovis){
 		super();
		this.idPostulacionFovis=idPostulacionFovis;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPostulacionFovis", idPostulacionFovis)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (JefeHogarModeloDTO) response.readEntity(JefeHogarModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public JefeHogarModeloDTO getResult() {
		return result;
	}

 
  	public void setIdPostulacionFovis (Long idPostulacionFovis){
 		this.idPostulacionFovis=idPostulacionFovis;
 	}
 	
 	public Long getIdPostulacionFovis (){
 		return idPostulacionFovis;
 	}
  
}