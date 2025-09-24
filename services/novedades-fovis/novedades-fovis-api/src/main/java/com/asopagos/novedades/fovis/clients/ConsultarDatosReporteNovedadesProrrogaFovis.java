package com.asopagos.novedades.fovis.clients;

import javax.ws.rs.core.GenericType;
import java.util.List;
import com.asopagos.novedades.fovis.dto.DatosReporteNovedadProrrogaFovisDTO;
import java.lang.Long;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.asopagos.services.common.ServiceClient;

/**
 * Metodo que hace la peticion REST al servicio GET
 * /rest/novedadesFovis/consultarDatosReporteNovedadesProrrogaFovis
 */
public class ConsultarDatosReporteNovedadesProrrogaFovis extends ServiceClient {
 
  
  	private Long fechaInicial;
  	private Long fechaFinal;
  
  	/** Atributo que almacena los datos resultado del llamado al servicio */
 	private List<DatosReporteNovedadProrrogaFovisDTO> result;
  
 	public ConsultarDatosReporteNovedadesProrrogaFovis (Long fechaInicial,Long fechaFinal){
 		super();
		this.fechaInicial=fechaInicial;
		this.fechaFinal=fechaFinal;
 	}
 
 	@Override
	protected Response invoke(WebTarget webTarget, String path) {
		Response response = webTarget.path(path)
									.queryParam("fechaInicial", fechaInicial)
						.queryParam("fechaFinal", fechaFinal)
						.request(MediaType.APPLICATION_JSON).get();
		return response;
	}
	
	
	@Override
	protected void getResultData(Response response) {
		this.result = (List<DatosReporteNovedadProrrogaFovisDTO>) response.readEntity(new GenericType<List<DatosReporteNovedadProrrogaFovisDTO>>(){});
	}
	
	/**
	 * Retorna el resultado del llamado al servicio
	 */
	 public List<DatosReporteNovedadProrrogaFovisDTO> getResult() {
		return result;
	}

 
  	public void setFechaInicial (Long fechaInicial){
 		this.fechaInicial=fechaInicial;
 	}
 	
 	public Long getFechaInicial (){
 		return fechaInicial;
 	}
  	public void setFechaFinal (Long fechaFinal){
 		this.fechaFinal=fechaFinal;
 	}
 	
 	public Long getFechaFinal (){
 		return fechaFinal;
 	}
  
}