package com.asopagos.legalizacionfovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import com.asopagos.dto.modelo.RecursoComplementarioModeloDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarListaRecursosComplementariosPostulacion
 */
public class ConsultarListaRecursosComplementariosPostulacion extends ServiceClient {
 
  
  	private Long idPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<RecursoComplementarioModeloDTO> result;
  
 	public ConsultarListaRecursosComplementariosPostulacion (Long idPostulacion){
 		super();
		this.idPostulacion=idPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idPostulacion", idPostulacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<RecursoComplementarioModeloDTO>) response.readEntity(new GenericType<List<RecursoComplementarioModeloDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<RecursoComplementarioModeloDTO> getResult() {
		return result;
	}

 
  	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  
}