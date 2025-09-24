package com.asopagos.aportes.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.aportes.dto.NovedadesProcesoAportesDTO;
import java.lang.Long;
import java.lang.Integer;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/aportes/consultarNovedadesPlanillasRegistrarProcesar
 */
public class ConsultarNovedadesPlanillasRegistrarProcesar extends ServiceClient {
 
  
  	private Long planillaAProcesar;
  	private Integer pagina;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<NovedadesProcesoAportesDTO> result;
  
 	public ConsultarNovedadesPlanillasRegistrarProcesar (Long planillaAProcesar,Integer pagina){
 		super();
		this.planillaAProcesar=planillaAProcesar;
		this.pagina=pagina;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("planillaAProcesar", planillaAProcesar)
						.queryParam("pagina", pagina)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<NovedadesProcesoAportesDTO>) response.readEntity(new GenericType<List<NovedadesProcesoAportesDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<NovedadesProcesoAportesDTO> getResult() {
		return result;
	}

 
  	public void setPlanillaAProcesar (Long planillaAProcesar){
 		this.planillaAProcesar=planillaAProcesar;
 	}
 	
 	public Long getPlanillaAProcesar (){
 		return planillaAProcesar;
 	}
  	public void setPagina (Integer pagina){
 		this.pagina=pagina;
 	}
 	
 	public Integer getPagina (){
 		return pagina;
 	}
  
}