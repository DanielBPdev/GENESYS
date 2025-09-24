package com.asopagos.legalizacionfovis.clients;

import java.lang.Long;
import com.asopagos.dto.modelo.RecursoComplementarioModeloDTO;
import com.asopagos.enumeraciones.fovis.TipoRecursoComplementarioEnum;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/legalizacionFovis/consultarRecursoComplementario
 */
public class ConsultarRecursoComplementario extends ServiceClient {
 
  
  	private TipoRecursoComplementarioEnum tipoRecursoComplementario;
  	private Long idPostulacion;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private RecursoComplementarioModeloDTO result;
  
 	public ConsultarRecursoComplementario (TipoRecursoComplementarioEnum tipoRecursoComplementario,Long idPostulacion){
 		super();
		this.tipoRecursoComplementario=tipoRecursoComplementario;
		this.idPostulacion=idPostulacion;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("tipoRecursoComplementario", tipoRecursoComplementario)
						.queryParam("idPostulacion", idPostulacion)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (RecursoComplementarioModeloDTO) response.readEntity(RecursoComplementarioModeloDTO.class);
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public RecursoComplementarioModeloDTO getResult() {
		return result;
	}

 
  	public void setTipoRecursoComplementario (TipoRecursoComplementarioEnum tipoRecursoComplementario){
 		this.tipoRecursoComplementario=tipoRecursoComplementario;
 	}
 	
 	public TipoRecursoComplementarioEnum getTipoRecursoComplementario (){
 		return tipoRecursoComplementario;
 	}
  	public void setIdPostulacion (Long idPostulacion){
 		this.idPostulacion=idPostulacion;
 	}
 	
 	public Long getIdPostulacion (){
 		return idPostulacion;
 	}
  
}