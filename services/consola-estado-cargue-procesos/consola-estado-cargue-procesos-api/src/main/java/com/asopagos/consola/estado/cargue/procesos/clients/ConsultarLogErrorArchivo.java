package com.asopagos.consola.estado.cargue.procesos.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.dto.ResultadoHallazgosValidacionArchivoDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/consolaEstadoCargueProcesos/consultarLogErrorArchivo
 */
public class ConsultarLogErrorArchivo extends ServiceClient {
 
  
  	private Long idConsola;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<ResultadoHallazgosValidacionArchivoDTO> result;
  
 	public ConsultarLogErrorArchivo (Long idConsola){
 		super();
		this.idConsola=idConsola;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idConsola", idConsola)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<ResultadoHallazgosValidacionArchivoDTO>) response.readEntity(new GenericType<List<ResultadoHallazgosValidacionArchivoDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<ResultadoHallazgosValidacionArchivoDTO> getResult() {
		return result;
	}

 
  	public void setIdConsola (Long idConsola){
 		this.idConsola=idConsola;
 	}
 	
 	public Long getIdConsola (){
 		return idConsola;
 	}
  
}