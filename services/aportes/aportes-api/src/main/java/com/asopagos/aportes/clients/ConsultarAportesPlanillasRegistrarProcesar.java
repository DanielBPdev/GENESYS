package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import java.lang.Long;
import java.lang.Integer;
import com.asopagos.aportes.dto.AporteDTO;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarAportesPlanillasRegistrarProcesar
 */
public class ConsultarAportesPlanillasRegistrarProcesar extends ServiceClient {
 
  
  	private Long idRegistroGeneral;
  	private Integer pagina;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<AporteDTO> result;
  
 	public ConsultarAportesPlanillasRegistrarProcesar (Long idRegistroGeneral,Integer pagina){
 		super();
		this.idRegistroGeneral=idRegistroGeneral;
		this.pagina=pagina;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("idRegistroGeneral", idRegistroGeneral)
						.queryParam("pagina", pagina)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<AporteDTO>) response.readEntity(new GenericType<List<AporteDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<AporteDTO> getResult() {
		return result;
	}

 
  	public void setIdRegistroGeneral (Long idRegistroGeneral){
 		this.idRegistroGeneral=idRegistroGeneral;
 	}
 	
 	public Long getIdRegistroGeneral (){
 		return idRegistroGeneral;
 	}
  	public void setPagina (Integer pagina){
 		this.pagina=pagina;
 	}
 	
 	public Integer getPagina (){
 		return pagina;
 	}
  
}